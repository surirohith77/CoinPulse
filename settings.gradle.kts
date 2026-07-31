pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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
}

rootProject.name = "CryptoTracker"

// Include app module
include(":app")

// Include core modules
include(":core:model")
include(":core:network")
include(":core:database")
include(":core:designsystem")

// Include feature modules
include(":feature:tracker")
include(":feature:detail")
