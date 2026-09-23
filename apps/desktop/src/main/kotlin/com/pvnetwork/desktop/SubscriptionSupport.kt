package com.pvnetwork.desktop

import com.pvnetwork.core.profile.PVProfile
import com.pvnetwork.core.profile.ProfileId
import com.pvnetwork.engine.xray.ShareLinkParser
import java.net.InetSocketAddress
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import java.time.Duration
import java.util.UUID
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

data class Subscription(
    val id: String,
    val name: String,
    val url: String,
    val lastUpdateEpochMillis: Long? = null,
    val lastStatus: String? = null,
)

data class SubscriptionUpdateReport(
    val added: Int,
    val failedLines: Int,
    val error: String? = null,
)

/**
 * Subscription registry persisted beside profiles; one encoded line per
 * subscription with the same corruption-bounded discipline.
 */
class SubscriptionRepository(private val file: Path) {

    @Volatile
    private var cache: List<Subscription> = load()

    fun all(): List<Subscription> = cache

    fun upsert(subscription: Subscription) {
        synchronized(this) {
            val next = cache.filterNot { it.id == subscription.id } + subscription
            persist(next)
            cache = next
        }
    }

    fun remove(id: String) {
        synchronized(this) {
            val next = cache.filterNot { it.id == id }
            persist(next)
            cache = next
        }
    }

    fun find(id: String): Subscription? = cache.firstOrNull { it.id == id }

    private fun load(): List<Subscription> {
        if (!Files.exists(file)) return emptyList()
        return Files.readAllLines(file, StandardCharsets.UTF_8).mapNotNull { line ->
            if (line.isBlank()) return@mapNotNull null
            runCatching { decode(line) }.getOrNull()
        }
    }

    private fun persist(subscriptions: List<Subscription>) {
        Files.createDirectories(file.parent)
        val tmp = file.resolveSibling(file.fileName.toString() + ".tmp")
        Files.newBufferedWriter(tmp, StandardCharsets.UTF_8).use { writer ->
            subscriptions.forEach { writer.write(encode(it)); writer.write("\n") }
        }
        Files.move(tmp, file, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE)
    }

    private fun encode(subscription: Subscription): String =
        listOf(
            "v1",
            subscription.id,
            subscription.name,
            subscription.url,
            subscription.lastUpdateEpochMillis?.toString() ?: "",
            subscription.lastStatus ?: "",
        ).joinToString("|") { it.replace("|", "%7C").replace("\n", "%0A") }

    private fun decode(line: String): Subscription {
        val parts = line.split('|')
        require(parts.size == 6 && parts[0] == "v1") { "unknown subscription line" }
        return Subscription(
            id = parts[1],
            name = parts[2],
            url = parts[3],
            lastUpdateEpochMillis = parts[4].toLongOrNull(),
            lastStatus = parts[5].ifBlank { null },
        )
    }
}

/**
 * Fetches a subscription URL and normalizes its payload into canonical
 * profiles: plain share-link lists and base64-encoded lists are both
 * accepted, each line is parsed by the engine share-link parsers, and the
 * original payload is never executed or persisted raw.
 */
class SubscriptionFetcher(
    private val parseLink: (String, ProfileId) -> PVProfile?,
) {
    private val http: HttpClient = HttpClient.newBuilder()
        .connectTimeout(Duration.ofSeconds(10))
        .followRedirects(HttpClient.Redirect.NORMAL)
        .build()

    fun fetch(url: String): Pair<List<PVProfile>, Int> {
        val trimmedUrl = url.trim()
        require(trimmedUrl.startsWith("http://") || trimmedUrl.startsWith("https://")) {
            "subscription url must be http(s)"
        }
        val request = HttpRequest.newBuilder(URI.create(trimmedUrl))
            .timeout(Duration.ofSeconds(20))
            .header("User-Agent", "PVNetwork/0.1")
            .GET()
            .build()
        val response = http.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8))
        require(response.statusCode() in 200..299) { "subscription http status ${response.statusCode()}" }

        val body = response.body().trim()
        val lines = body.lineSequence()
            .map(String::trim)
            .filter(String::isNotBlank)
            .toList()
            .takeIf { it.any { line -> line.contains("://") } }
            ?: decodeBody(body)?.lineSequence()
                ?.map(String::trim)
                ?.filter(String::isNotBlank)
                ?.toList()
            ?: throw IllegalArgumentException("subscription payload contains no share links")

        val profiles = mutableListOf<PVProfile>()
        var failed = 0
        lines.forEach { line ->
            val profile = runCatching {
                parseLink(line, ProfileId(UUID.randomUUID().toString()))
            }.getOrNull()
            if (profile != null) {
                profiles += profile
            } else {
                failed++
            }
        }
        require(profiles.isNotEmpty()) { "no parsable share link found in subscription" }
        return profiles to failed
    }

    @OptIn(ExperimentalEncodingApi::class)
    private fun decodeBody(body: String): String? = try {
        val cleaned = body.filterNot(Char::isWhitespace)
        val padded = when (cleaned.length % 4) {
            2 -> "$cleaned=="
            3 -> "$cleaned"
            else -> cleaned
        }
        val normalized = padded.replace('-', '+').replace('_', '/')
        Base64.Default.decode(normalized).decodeToString()
    } catch (_: Throwable) {
        null
    }
}

/** Honest endpoint latency: full TCP connect timing against the profile endpoint. */
object LatencyTester {
    const val TIMEOUT_MILLIS = 3_000

    fun tcpPingMillis(profile: PVProfile): Long? = try {
        val socket = java.net.Socket()
        socket.tcpNoDelay = true
        val start = System.nanoTime()
        socket.connect(InetSocketAddress(profile.endpoint.host, profile.endpoint.port), TIMEOUT_MILLIS)
        val elapsed = (System.nanoTime() - start) / 1_000_000
        socket.close()
        elapsed
    } catch (_: Throwable) {
        null
    }
}
