@file:OptIn(ExperimentalStdlibApi::class)

import com.android.build.api.dsl.LibraryExtension
import com.android.build.gradle.internal.cxx.logging.warnln
import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.goggleService)
    alias(libs.plugins.googleFirebaseCrashlytics)
    alias(libs.plugins.play.publishing)
}

val versionCodeAndName: Pair<String, Int>
    get() {
        val properties = Properties()
            .apply {
                load(rootProject.file("version.properties").inputStream())
            }
        val versionName = properties.getProperty("VERSION")
        val versionCode = versionName.split(".")
            .map { it.toInt() }
            .let { (a, b, c) -> a * 100000 + b * 1000 + c }
        return Pair(versionName, versionCode)
    }


android {
    namespace = "org.corexero.indianmetro.metro"
    compileSdk = 36

    defaultConfig {
        applicationId = "org.corexero.indianmetro.metro"
        minSdk = 24
        targetSdk = 36
        versionCode = versionCodeAndName.second
        versionName = versionCodeAndName.first

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    flavorDimensions.add("city")

    productFlavors {
        City.values().forEach { city ->
            create(city.name.lowercase()) {
                dimension = "city"
                applicationIdSuffix = city.name.lowercase()
                versionNameSuffix = "-${city.name.lowercase()}"
                buildConfigField(
                    "String",
                    "CITY",
                    "\"${city.name}\""
                )
            }
        }
    }

    fun prop(name: String) = project.findProperty(name) as String

    signingConfigs {
        create("debugConfig") {
            storeFile = rootProject.file(prop("DEBUG_STORE_FILE"))
            storePassword = prop("DEBUG_STORE_PASSWORD")
            keyAlias = prop("DEBUG_KEY_ALIAS")
            keyPassword = prop("DEBUG_KEY_PASSWORD")
        }

        create("releaseConfig") {
            storeFile = rootProject.file(prop("RELEASE_STORE_FILE"))
            storePassword = prop("RELEASE_STORE_PASSWORD")
            keyAlias = prop("RELEASE_KEY_ALIAS")
            keyPassword = prop("RELEASE_KEY_PASSWORD")
        }
    }

    buildTypes {
        debug {
            defaultConfig.versionName += ".dev"
            signingConfig = signingConfigs.getByName("debugConfig")
        }
        release {
            signingConfig = signingConfigs.getByName("releaseConfig")
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

play {
    serviceAccountCredentials.set(file("${rootDir}/play-service-account.json"))
    track.set("internal") // ya closed / production agar chahiye
}

dependencies {
    implementation(projects.indianmetrocore)
    implementation(projects.sutradhar)
    implementation(projects.metroUi)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.material)
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

rootProject.project(":nativelib").pluginManager.withPlugin("com.android.library") {
    rootProject.project(":nativelib").extensions.configure<LibraryExtension> {
        defaultConfig {
            val properties = Properties()
            val localPropertiesFile = project.file("${project.projectDir}/nativeLib.properties")
            if (localPropertiesFile.exists()) {
                properties.load(localPropertiesFile.inputStream())
            } else {
                throw Error("${project.projectDir}/nativeLib.properties file is missing!")
            }

            //noinspection WrongGradleMethod
            val selectedFlavour = android.productFlavors.names.find {
                gradle.startParameter.taskNames.any { taskName ->
                    taskName.contains(it, ignoreCase = true)
                }
            } as? String
            val debugSha = properties.getProperty("DEBUG_CERT_SHA256") as String
            val releaseSha = properties.getProperty("RELEASE_CERT_SHA256") as String

            val platStoreSha =
                selectedFlavour?.let {
                    properties.getProperty("${it.uppercase()}_PLAY_STORE_SHA256") as String
                }

            val applicationId = android.namespace + "." + selectedFlavour
            val allowedCerts = if (platStoreSha == null) {
                warnln("No Play Store SHA found for $selectedFlavour")
                listOf(debugSha, releaseSha).joinToString(";")
            } else {
                listOf(debugSha, releaseSha, platStoreSha).joinToString(";")
            }
            externalNativeBuild {
                cmake {
                    cppFlags("")
                    arguments += listOf(
                        "-DEXPECTED_PKG=${applicationId}",
                        "-DALLOWED_CERTS=${allowedCerts}"
                    )
                }
            }
        }
    }
}

enum class City {
    Mumbai,
    Delhi,
    Chennai,
    Bengaluru,
    Hyderabad,
    Kolkata,
    Kochi,
    Pune,
    Lucknow,
    Ahmedabad,
    Jaipur,
    Kanpur,
    Nagpur,
    Agra
}

// ---------- per-flavour DB generation (only for active flavours) ----------
val scriptRelativePath = "scripts/split-city.js" // adjust if your script is elsewhere
val defaultSourceDb = "${rootDir.resolve("data").resolve("metro.sqlite")}"
val sourceDbProp: String = (project.findProperty("sourceDb") as? String) ?: defaultSourceDb

// If you want to write into src/<flavour>/assets (mutates source), set this true.
// Otherwise leave false to write into build/generated/assets/<flavour> (recommended).
val writeIntoSrc: Boolean = (project.findProperty("writeIntoSrc") as? String)?.toBoolean() ?: true

// Helper: allow overriding cityId for a flavor with -PcityId.<flavour>=<id> or in gradle.properties
fun resolvedCityIdFor(flavorName: String, defaultIdx: Int): String {
    val p = project.findProperty("cityId.$flavorName") as? String
    return p ?: defaultIdx.toString()
}

// Build list of flavour names from your enum. Keep in sync with your City enum.
val flavours = City.values().map { it.name.lowercase() } // City enum already in your build file

// Find flavors that are being built in this invocation (task names contain the flavor)
val requestedTaskNames = gradle.startParameter.taskNames.map { it.lowercase() }
val requestedFlavours = flavours.filter { flavour ->
    requestedTaskNames.any { tn -> tn.contains(flavour) }
}

// If no explicit flavour found in task names, we won't perform generation (avoid doing everything unexpectedly).
if (requestedFlavours.isEmpty()) {
    logger.info("No city flavour detected in invoked Gradle tasks — DB generation skipped. " +
            "Run a specific flavour task (eg. assembleMumbaiDebug) or set -PforceGenerateAll=true")
} else {
    // For each requested flavour create and run a task
    requestedFlavours.forEachIndexed { idx, flavour ->
        val cityIdx = City.values().indexOfFirst { it.name.lowercase() == flavour }.let { if (it >= 0) it else idx }
        val taskName = "generateDbFor${flavour.replaceFirstChar { it.uppercase() }}"
        val outDirSrc = file("${projectDir}/src/$flavour/assets")
        val outDirBuild = file("${buildDir}/generated/assets/$flavour")
        val outDir = if (writeIntoSrc) outDirSrc else outDirBuild

        tasks.register<Exec>(taskName) {
            group = "generation"
            description = "Generate DB for flavour '$flavour' -> ${outDir.absolutePath}"

            // ensure outDir exists
            doFirst {
                if (!outDir.exists()) outDir.mkdirs()
            }

            // resolved city id (allow per flavour override)
            val cityId = resolvedCityIdFor(flavour, cityIdx)

            // node script path (absolute)
            val scriptPath = rootDir.resolve(scriptRelativePath).absolutePath
            // command line - uses 'node' on PATH. Set full node path if required.
            commandLine("node", scriptPath, sourceDbProp, cityId, outDir.absolutePath)

            // up-to-date checks: consider the input combined DB and the script
            inputs.file(file(sourceDbProp))
            inputs.file(file(scriptPath))
            outputs.dir(outDir)
        }

        // ensure preBuild depends on this task so it runs before packaging
        tasks.named("preBuild").configure { dependsOn(taskName) }
    }
}
// ---------- end per-flavour DB generation ----------
