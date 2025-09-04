import com.android.build.gradle.LibraryExtension
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.goggleService)
    alias(libs.plugins.googleFirebaseCrashlytics)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "HyderabadApp"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(dependencies.platform(libs.firebase.bom))
            implementation(libs.firebase.crashlytics)
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(projects.nativelib)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel.compose)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(projects.indianmetrocore)
            implementation(projects.metroUi)
            implementation(projects.sutradhar)
            api(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "org.corexero.indianmetro.hyderabad"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "org.corexero.indianmetro.hyderabad"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
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
            val debugSha = properties.getProperty("DEBUG_CERT_SHA256") as String
            val releaseSha = properties.getProperty("RELEASE_CERT_SHA256") as String
            val applicationId = android.namespace
            if (applicationId == null) {
                throw Error("ApplicationId/Namespace not found! Make sure you have set the namespace in the android block.")
            }
            val allowedCerts = listOf(debugSha, releaseSha).joinToString(";")
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


dependencies {
    debugImplementation(compose.uiTooling)
}

