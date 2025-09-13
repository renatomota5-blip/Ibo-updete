// build.gradle.kts (raiz do projeto)

plugins {
    // Plugin do Android e Kotlin serão aplicados no módulo "app"
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.hilt) apply false
}

task<Delete>("clean") {
    delete(rootProject.buildDir)
}
