import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("multiplatform")
    id("com.android.kotlin.multiplatform.library")
}

kotlin {
    android {
        namespace = "com.pvnetwork.engine.xray"
        compileSdk = 36
        minSdk = 26
    }

    jvm {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    jvmToolchain(21)

    sourceSets {
        commonMain.dependencies {
            implementation(project(":core:foundation"))
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}
