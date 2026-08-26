import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("multiplatform")
}

kotlin {
    jvm {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    jvmToolchain(21)
    sourceSets {
        commonMain.dependencies { implementation(project(":core:foundation")) }
        commonTest.dependencies { implementation(kotlin("test")) }
    }
}
