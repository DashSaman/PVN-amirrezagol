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
 * skipped, never allowed to break the whole store. Format v2 carries the
 * owning subscription id; v1 lines load with a null subscription id.
 */
class ProfileRepository(private val file: Path) {

    @Volatile
    private var cache: List<PVProfile> = load()

    fun profiles(): List<PVProfile> = cache

    fun subscriptionIdOf(profile: PVProfile): String? = subscriptionIds[profile.id.value]

    fun add(profile: PVProfile, subscriptionId: String? = null) {
        synchronized(this) {
            val next = cache + profile
            persist(next, subscriptionIds + (profile.id.value to subscriptionId))
            cache = next
        }
    }

    fun addAll(profiles: List<PVProfile>, subscriptionId: String) {
        synchronized(this) {
            val next = cache + profiles
            persist(next, subscriptionIds + profiles.associate { it.id.value to subscriptionId })
            cache = next
        }
    }

    fun remove(id: ProfileId) {
        synchronized(this) {
            val next = cache.filterNot { it.id == id }
            persist(next, subscriptionIds - id.value)
            cache = next
        }
    }

    fun removeWhere(predicate: (PVProfile) -> Boolean): List<PVProfile> {
        synchronized(this) {
            val removed = cache.filter(predicate)
            val next = cache.filterNot(predicate)
            persist(next, subscriptionIds - removed.map { it.id.value }.toSet())
            cache = next
            return removed
        }
    }

    fun find(id: ProfileId): PVProfile? = cache.firstOrNull { it.id == id }

    fun nextId(): ProfileId = ProfileId(UUID.randomUUID().toString())

    private var subscriptionIds: MutableMap<String, String?> = linkedMapOf()

    private fun load(): List<PVProfile> {
        if (!Files.exists(file)) return emptyList()
        val result = mutableListOf<PVProfile>()
        val subs = linkedMapOf<String, String?>()
        Files.readAllLines(file, StandardCharsets.UTF_8).forEachIndexed { index, line ->
            if (line.isBlank()) return@forEachIndexed
            try {
                val (profile, subId) = decode(line)
                result += profile
                subs[profile.id.value] = subId
            } catch (_: Throwable) {
                System.err.println("profile store: skipping corrupt line ${index + 1}")
            }
        }
        subscriptionIds = subs
        return result
    }

    private fun persist(profiles: List<PVProfile>, subs: Map<String, String?>) {
        Files.createDirectories(file.parent)
        val tmp = file.resolveSibling(file.fileName.toString() + ".tmp")
        Files.newBufferedWriter(tmp, StandardCharsets.UTF_8).use { writer ->
            profiles.forEach { profile ->
                writer.write(encode(profile, subs[profile.id.value]))
                writer.write("\n")
            }
        }
        Files.move(tmp, file, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE)
        subscriptionIds = linkedMapOf<String, String?>().apply { putAll(subs) }
    }

    private fun encode(profile: PVProfile, subscriptionId: String?): String {
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
            enc(subscriptionId ?: ""),
            secrets,
            extensions,
        ).joinToString("|")
    }

    private fun decode(line: String): Pair<PVProfile, String?> {
        val parts = line.split('|')
        val subscriptionId: String?
        when {
            parts.size == 10 && parts[0] == "v2" -> subscriptionId = parts[7].ifBlank { null }
            parts.size == 9 && parts[0] == "v1" -> subscriptionId = null
            else -> throw IllegalArgumentException("unknown profile line format")
        }
        val secretsPart = if (parts.size == 10) parts[8] else parts[7]
        val extensionsPart = if (parts.size == 10) parts[9] else parts[8]
        val secrets = secretsPart.split(';').filter(String::isNotBlank).associate {
            val (k, v) = it.split('=', limit = 2)
            dec(k) to SecretRef(dec(v))
        }
        val extensions = extensionsPart.split(';').filter(String::isNotBlank).associate {
            val (k, v) = it.split('=', limit = 2)
            dec(k) to dec(v)
        }
        val profile = PVProfile(
            id = ProfileId(dec(parts[1])),
            displayName = dec(parts[2]),
            protocolId = dec(parts[3]),
            endpoint = Endpoint(dec(parts[4]), parts[5].toInt()),
            secretRefs = secrets,
            extensions = extensions,
            origin = ProfileOrigin.valueOf(parts[6]),
        )
        return profile to subscriptionId
    }

    private fun enc(value: String): String = URLEncoder.encode(value, StandardCharsets.UTF_8)
    private fun dec(value: String): String = URLDecoder.decode(value, StandardCharsets.UTF_8)

    companion object {
        private const val FORMAT_VERSION = "v2"
    }
}
