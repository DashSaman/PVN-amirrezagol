import java.net.URI
import java.security.MessageDigest
import java.util.zip.ZipInputStream

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.pvnetwork.client"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.pvnetwork.client"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "0.1.0"
        ndk { abiFilters += "arm64-v8a" }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    buildFeatures {
        compose = true
    }

    packaging {
        jniLibs {
            useLegacyPackaging = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation(project(":core:foundation"))
    implementation(project(":engines:xray-adapter"))

    val composeBom = platform("androidx.compose:compose-bom:2026.09.00")
    implementation(composeBom)
    implementation("androidx.activity:activity-compose:1.13.0")
    implementation("androidx.compose.foundation:foundation")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.core:core-ktx:1.19.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.9.4")
}

// Xray-core engine binary (MPL-2.0) is fetched at build time from the pinned
// official release with an exact sha256 gate; it is executed from
// nativeLibraryDir as an independent process, keeping license scope separate.
val xrayCoreVersion = "26.7.28"
val xrayCoreZipSha256 = "a442892c175fa648fc56866ec872aac441c5a6b8946a1b60f0258ae16a7fb402"
val xrayCoreUrl = "https://github.com/XTLS/Xray-core/releases/download/v$xrayCoreVersion/Xray-android-arm64-v8a.zip"
val xrayTargetFile = File(projectDir, "src/main/jniLibs/arm64-v8a/libxray.so")

val fetchXrayCore = tasks.register("fetchXrayCore") {
    val target = xrayTargetFile
    val coreUrl = xrayCoreUrl
    val expectedSha = xrayCoreZipSha256
    val zipCacheFile = File(projectDir, "build/xray/Xray-android-arm64.zip")
    outputs.file(target)
    doLast {
        fun digestOf(file: File): String =
            MessageDigest.getInstance("SHA-256").digest(file.readBytes())
                .joinToString("") { "%02x".format(it) }

        if (target.exists()) return@doLast
        val zipFile = zipCacheFile
        zipFile.parentFile.mkdirs()
        if (!zipFile.exists() || digestOf(zipFile) != expectedSha) {
            zipFile.outputStream().use { out ->
                URI(coreUrl).toURL().openStream().use { it.copyTo(out) }
            }
        }
        val actual = digestOf(zipFile)
        require(actual == expectedSha) { "Xray-core zip sha256 mismatch: $actual" }
        target.parentFile.mkdirs()
        ZipInputStream(zipFile.inputStream().buffered()).use { stream ->
            var entry = stream.nextEntry
            while (entry != null) {
                if (!entry.isDirectory && entry.name == "xray") {
                    target.outputStream().use { out -> stream.copyTo(out) }
                    break
                }
                entry = stream.nextEntry
            }
        }
        require(target.exists()) { "xray binary extraction failed" }
    }
}
tasks.matching { it.name == "preBuild" }.configureEach {
    dependsOn(fetchXrayCore)
}
