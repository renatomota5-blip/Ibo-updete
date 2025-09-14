// build.gradle.kts (raiz do projeto)

plugins {
    // Versões alinhadas ao seu projeto/GitHub Action
    id("com.android.application") version "8.4.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.24" apply false
    id("com.google.dagger.hilt.android") version "2.51.1" apply false
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}
