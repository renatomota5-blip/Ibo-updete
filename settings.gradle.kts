// settings.gradle.kts — ÚNICO ponto que define o catálogo 'libs'

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
    versionCatalogs {
        create("libs") {
            // 👇 Apenas UMA chamada 'from'. Não adicione outra.
            from(files("gradle/libs.versions.toml"))
        }
    }
}

rootProject.name = "IboPlusApp"
include(":app")
