dependencies {
    // ---- AndroidX base
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")

    // ---- Compose (compatível com Kotlin 1.9.24)
    val composeUi = "1.6.8"
    implementation("androidx.activity:activity-compose:1.9.2")
    implementation("androidx.compose.ui:ui:$composeUi")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeUi")
    implementation("androidx.compose.foundation:foundation:$composeUi")
    implementation("androidx.compose.material3:material3:1.2.1")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeUi")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$composeUi")

    // Navegação Compose
    implementation("androidx.navigation:navigation-compose:2.8.0")

    // Coil para imagens
    implementation("io.coil-kt:coil-compose:2.6.0")

    // ---- Lifecycle
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.6")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.6")

    // ---- Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")

    // ---- WorkManager
    implementation("androidx.work:work-runtime-ktx:2.9.1")

    // ---- Hilt (DI)
    implementation("com.google.dagger:hilt-android:2.51.1")
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    implementation("androidx.hilt:hilt-work:1.2.0")
    kapt("androidx.hilt:hilt-compiler:1.2.0")

    // ---- ExoPlayer
    implementation("com.google.android.exoplayer:exoplayer:2.19.1")

    // ---- Retrofit + Gson (FALTAVAM — essenciais p/ @GET/@Query/@SerializedName)
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // ---- Timber
    implementation("com.jakewharton.timber:timber:5.0.1")
}
