pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "PVNetwork"

include(":core:foundation")
project(":core:foundation").projectDir = file("core/foundation")

include(":apps:desktop")
project(":apps:desktop").projectDir = file("apps/desktop")
