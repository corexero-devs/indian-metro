@file:OptIn(ExperimentalStdlibApi::class)

import com.android.build.gradle.LibraryExtension
import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.goggleService)
    alias(libs.plugins.googleFirebaseCrashlytics)
}

android {
    namespace = "org.corexero.indianmetro.metro"
    compileSdk = 36

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//        consumerProguardFiles("consumer-rules.pro")
    }

    flavorDimensions.add("city")

    productFlavors {
        City.values().forEach { city ->
            create(city.name.lowercase()) {
                dimension = "city"
                applicationIdSuffix = city.name.lowercase()
                versionNameSuffix = "-${city.name.lowercase()}"
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
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
}

dependencies {
    implementation(projects.indianmetrocore)
    implementation(projects.nativelib)
    implementation(projects.sutradhar)
    api(projects.metroUi)
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