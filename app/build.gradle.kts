plugins {
    id 'com.android.application'
    id 'org.jetbrains.kotlin.android'
    id 'kotlin-kapt'
    id 'dagger.hilt.android.plugin'
}

android {
    namespace "com.iboplus.app"
    compileSdk 34

    defaultConfig {
        applicationId "com.iboplus.app"
        minSdk 21
        targetSdk 34
        versionCode 1
        versionName "1.0"
    }
    buildFeatures {
        compose true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
}

dependencies {
    // Hilt
    implementation "com.google.dagger:hilt-android:2.51.1"
    kapt "com.google.dagger:hilt-android-compiler:2.51.1"

    // Lifecycle
    implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.6"
    implementation "androidx.lifecycle:lifecycle-runtime-ktx:2.8.6"

    // Compose integration com Hilt
    implementation "androidx.hilt:hilt-navigation-compose:1.2.0"

    // Coroutines
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1"
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1"
}
