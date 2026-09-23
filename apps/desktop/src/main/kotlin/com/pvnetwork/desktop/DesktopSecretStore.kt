package com.pvnetwork.desktop

import com.pvnetwork.core.profile.SecretRef
import com.pvnetwork.core.security.SecretPurpose
import com.pvnetwork.core.security.SecretStore
import com.pvnetwork.core.security.clearSecret
import com.sun.jna.platform.win32.Crypt32Util
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.attribute.PosixFilePermissions
import java.util.UUID
import kotlin.io.path.exists

/**
 * Desktop secret boundary. On Windows every value is DPAPI-protected for the
 * current user before it touches disk. On POSIX hosts the file is created
 * owner-only (0600); desktop Linux secure-storage integration remains a
 * documented follow-up.
 */
class DesktopSecretStore(private val directory: Path) : SecretStore {

    private val isWindows = System.getProperty("os.name").orEmpty()
        .lowercase().contains("windows")

    init {
        Files.createDirectories(directory)
    }

    override fun put(purpose: SecretPurpose, secret: CharArray): SecretRef {
        val ref = SecretRef("secret://desktop/${UUID.randomUUID()}")
        val file = fileFor(ref)
        val plain = String(secret).toByteArray(StandardCharsets.UTF_8)
        val stored = if (isWindows) Crypt32Util.cryptProtectData(plain) else plain
        if (isWindows) {
            Files.write(file, stored)
        } else {
            val attrs = java.nio.file.attribute.PosixFilePermissions.asFileAttribute(
                setOf(
                    java.nio.file.attribute.PosixFilePermission.OWNER_READ,
                    java.nio.file.attribute.PosixFilePermission.OWNER_WRITE,
                ),
            )
            Files.createFile(file, attrs)
            Files.write(file, stored)
        }
        return ref
    }

    override fun <T> withSecret(ref: SecretRef, block: (CharArray) -> T): T? {
        val file = fileFor(ref)
        if (!file.exists()) return null
        val stored = Files.readAllBytes(file)
        val plain = if (isWindows) {
            try {
                Crypt32Util.cryptUnprotectData(stored)
            } catch (_: Throwable) {
                return null
            }
        } else {
            stored
        }
        val chars = String(plain, StandardCharsets.UTF_8).toCharArray()
        return try {
            block(chars)
        } finally {
            chars.clearSecret()
        }
    }

    override fun delete(ref: SecretRef): Boolean {
        val file = fileFor(ref)
        return Files.deleteIfExists(file)
    }

    private fun fileFor(ref: SecretRef): Path {
        val safe = ref.value.replace(Regex("[^A-Za-z0-9._-]"), "_")
        return directory.resolve("$safe.bin")
    }
}
