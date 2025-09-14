// settings.gradle.kts — define o catálogo como "appLibs" (evita colisões com "libs")

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
        create("appLibs") {
            from(files("gradle/libs.versions.toml"))
        }
    }
}

rootProject.name = "IboPlusApp"
include(":app")
