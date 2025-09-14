plugins {
    // Android Gradle Plugin
    id("com.android.application") version "8.4.0" apply false

    // Kotlin
    id("org.jetbrains.kotlin.android") version "1.9.24" apply false
    id("kotlin-kapt") version "1.9.24" apply false

    // Hilt
    id("com.google.dagger.hilt.android") version "2.51.1" apply false
}
