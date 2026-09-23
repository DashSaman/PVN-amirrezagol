package com.pvnetwork.desktop

import com.pvnetwork.core.profile.Endpoint
import com.pvnetwork.core.profile.PVProfile
import com.pvnetwork.core.profile.ProfileId
import com.pvnetwork.core.profile.ProfileOrigin
import com.pvnetwork.core.profile.SecretRef
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import java.util.UUID

/**
 * Flat-file profile persistence. One encoded line per profile keeps the
 * format inspectable and corruption-bounded: a bad line is reported and
 * skipped, never allowed to break the whole store.
 */
class ProfileRepository(private val file: Path) {

    @Volatile
    private var cache: List<PVProfile> = load()

    fun profiles(): List<PVProfile> = cache

    fun add(profile: PVProfile) {
        synchronized(this) {
            val next = cache + profile
            persist(next)
            cache = next
        }
    }

    fun remove(id: ProfileId) {
        synchronized(this) {
            val next = cache.filterNot { it.id == id }
            persist(next)
            cache = next
        }
    }

    fun find(id: ProfileId): PVProfile? = cache.firstOrNull { it.id == id }

    fun nextId(): ProfileId = ProfileId(UUID.randomUUID().toString())

    private fun load(): List<PVProfile> {
        if (!Files.exists(file)) return emptyList()
        val result = mutableListOf<PVProfile>()
        Files.readAllLines(file, StandardCharsets.UTF_8).forEachIndexed { index, line ->
            if (line.isBlank()) return@forEachIndexed
            try {
                result += decode(line)
            } catch (_: Throwable) {
                System.err.println("profile store: skipping corrupt line ${index + 1}")
            }
        }
        return result
    }

    private fun persist(profiles: List<PVProfile>) {
        Files.createDirectories(file.parent)
        val tmp = file.resolveSibling(file.fileName.toString() + ".tmp")
        Files.newBufferedWriter(tmp, StandardCharsets.UTF_8).use { writer ->
            profiles.forEach { writer.write(encode(it)); writer.write("\n") }
        }
        Files.move(tmp, file, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE)
    }

    private fun encode(profile: PVProfile): String {
        val secrets = profile.secretRefs.entries.joinToString(";") {
            "${enc(it.key)}=${enc(it.value.value)}"
        }
        val extensions = profile.extensions.entries.joinToString(";") {
            "${enc(it.key)}=${enc(it.value)}"
        }
        return listOf(
            FORMAT_VERSION,
            enc(profile.id.value),
            enc(profile.displayName),
            enc(profile.protocolId),
            enc(profile.endpoint.host),
            profile.endpoint.port.toString(),
            profile.origin.name,
            secrets,
            extensions,
        ).joinToString("|")
    }

    private fun decode(line: String): PVProfile {
        val parts = line.split('|')
        require(parts.size == 9 && parts[0] == FORMAT_VERSION) { "unknown profile line format" }
        val secrets = parts[7].split(';').filter(String::isNotBlank).associate {
            val (k, v) = it.split('=', limit = 2)
            dec(k) to SecretRef(dec(v))
        }
        val extensions = parts[8].split(';').filter(String::isNotBlank).associate {
            val (k, v) = it.split('=', limit = 2)
            dec(k) to dec(v)
        }
        return PVProfile(
            id = ProfileId(dec(parts[1])),
            displayName = dec(parts[2]),
            protocolId = dec(parts[3]),
            endpoint = Endpoint(dec(parts[4]), parts[5].toInt()),
            secretRefs = secrets,
            extensions = extensions,
            origin = ProfileOrigin.valueOf(parts[6]),
        )
    }

    private fun enc(value: String): String = URLEncoder.encode(value, StandardCharsets.UTF_8)
    private fun dec(value: String): String = URLDecoder.decode(value, StandardCharsets.UTF_8)

    companion object {
        private const val FORMAT_VERSION = "v1"
    }
}
