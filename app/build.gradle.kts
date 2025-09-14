plugins {
    alias(appLibs.plugins.android.application)
    alias(appLibs.plugins.kotlin.android)
    alias(appLibs.plugins.kotlin.kapt)
    alias(appLibs.plugins.hilt)
}

android {
    namespace = "com.iboplus.app"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.iboplus.app"
        minSdk = 21
        targetSdk = 35
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isDebuggable = true
        }
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs += listOf(
            "-Xjvm-default=all",
            "-opt-in=kotlin.RequiresOptIn"
        )
    }
}

dependencies {

    // --- Retrofit / OkHttp
    implementation(appLibs.retrofit)
    implementation(appLibs.converter.gson)
    implementation(appLibs.okhttp)
    implementation(appLibs.logging.interceptor)

    // --- ExoPlayer
    implementation(appLibs.exoplayer.core)
    implementation(appLibs.exoplayer.ui)

    // --- Compose
    implementation(platform(appLibs.compose.bom))
    implementation(appLibs.compose.material3)
    implementation(appLibs.compose.ui)
    implementation(appLibs.compose.ui.tooling)
    implementation(appLibs.compose.preview)
    implementation(appLibs.activity.compose)
    implementation(appLibs.lifecycle.runtime.ktx)
    implementation(appLibs.navigation.compose)

    // --- AndroidX Core / Lifecycle
    implementation(appLibs.core.ktx)
    implementation(appLibs.lifecycle.viewmodel.ktx)

    // --- Hilt
    implementation("com.google.dagger:hilt-android:${appLibs.versions.hilt.get()}")
    kapt("com.google.dagger:hilt-android-compiler:${appLibs.versions.hilt.get()}")

    // --- WorkManager + Hilt
    implementation(appLibs.work.runtime.ktx)
    implementation(appLibs.work.hilt)
    kapt(appLibs.work.hilt.compiler)

    // --- Timber (logs usados em App.kt)
    implementation("com.jakewharton.timber:timber:5.0.1")

    // Tests
    testImplementation(appLibs.junit)
    androidTestImplementation(appLibs.androidx.junit)
    androidTestImplementation(appLibs.espresso.core)
    androidTestImplementation(platform(appLibs.compose.bom))
    androidTestImplementation(appLibs.compose.ui.test.junit4)
    debugImplementation(appLibs.compose.ui.tooling)
    debugImplementation(appLibs.compose.ui.test.manifest)
}
