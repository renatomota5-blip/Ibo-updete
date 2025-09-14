// settings.gradle.kts

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
            // Carrega SOMENTE deste arquivo TOML (uma única vez)
            from(files("gradle/libs.versions.toml"))
        }
    }
}

rootProject.name = "IboPlusApp"
include(":app")
