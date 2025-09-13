# =========================
# ProGuard / R8 - IBO Plus
# =========================

# Mantém anotações e generics (úteis para Gson/Retrofit/Hilt/Compose)
-keepattributes *Annotation*, Signature, InnerClasses, EnclosingMethod

# --------
# Hilt/Dagger
# --------
# O plugin já injeta regras, mas mantemos reforços seguros:
-keep class dagger.hilt.** { *; }
-keep class androidx.hilt.** { *; }
-keep class dagger.internal.codegen.** { *; }
-dontwarn javax.annotation.**

# --------
# Retrofit / OkHttp / Gson
# --------
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**

# Modelos Gson do app (evita remoção de campos)
# Ajuste o pacote se necessário quando criarmos os DTOs
-keep class com.iboplus.app.data.model.** { *; }

# Interfaces Retrofit (mantenha assinaturas)
-keep interface com.iboplus.app.data.api.** { *; }

# Converter Gson
-keep class com.google.gson.stream.** { *; }

# --------
# ExoPlayer
# --------
-dontwarn com.google.android.exoplayer2.**
-keep class com.google.android.exoplayer2.** { *; }

# --------
# Jetpack Compose
# --------
# Compose normalmente funciona sem regras extras, mas estas ajudam a evitar
# remoção agressiva em previews/inspeções.
-keep class androidx.compose.** { *; }
-keep class androidx.activity.compose.** { *; }
-dontwarn androidx.compose.**

# --------
# AndroidX / Lifecycle
# --------
-dontwarn androidx.lifecycle.**
-keep class androidx.lifecycle.** { *; }
-keepclassmembers class * {
    @androidx.lifecycle.* <methods>;
}

# --------
# ZXing / ML Kit (se viermos a usar QR por libs externas)
# --------
-dontwarn com.google.zxing.**
-keep class com.google.zxing.** { *; }

# --------
# Geral
# --------
# Mantém classes de aplicação e Activities/Fragments/Services/Broadcast/Providers
-keep class ** extends android.app.Application { *; }
-keep class ** extends android.app.Activity { *; }
-keep class ** extends androidx.activity.ComponentActivity { *; }
-keep class ** extends androidx.fragment.app.Fragment { *; }
-keep class ** extends android.app.Service { *; }
-keep class ** extends android.content.BroadcastReceiver { *; }
-keep class ** extends android.content.ContentProvider { *; }

# Evita remoção de recursos de reflexão comuns
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}
