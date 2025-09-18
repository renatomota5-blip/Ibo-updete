plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "com.iboplus.app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.iboplus.app"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        // WorkManager: necessário em alguns casos com processos em segundo plano
        // multiDexEnabled = true // habilite se o app passar do limite 64K
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    // Caso use viewBinding/compose, habilite aqui
    // buildFeatures { viewBinding = true }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")

    // Core AndroidX
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")

    // WorkManager (KTX + coroutines) -> Necessário pro CoroutineWorker
    implementation("androidx.work:work-runtime-ktx:2.9.1")

    // Coroutines (opcional mas recomendado para tasks)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")

    // (se você usa Hilt/Dagger, pode manter, mas NÃO é obrigatório para o Worker simples)
    // implementation("com.google.dagger:hilt-android:2.52")
    // kapt("com.google.dagger:hilt-compiler:2.52")
}
