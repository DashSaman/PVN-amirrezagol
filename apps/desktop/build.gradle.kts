import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
}

kotlin {
    jvmToolchain(21)
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

dependencies {
    implementation(project(":core:foundation"))
    implementation(compose.desktop.currentOs)
    implementation(compose.material)
    testImplementation(kotlin("test"))
}

compose.desktop {
    application {
        mainClass = "com.pvnetwork.desktop.MainKt"
        nativeDistributions {
            packageName = "PVNetwork"
            packageVersion = "0.1.0"
            description = "PVNetwork desktop client shell"
        }
    }
}
