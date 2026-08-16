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

include(":engines:wireguard-adapter")
project(":engines:wireguard-adapter").projectDir = file("engines/wireguard-adapter")
