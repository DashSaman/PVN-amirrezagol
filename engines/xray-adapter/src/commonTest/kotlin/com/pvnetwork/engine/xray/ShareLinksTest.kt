package com.pvnetwork.engine.xray

import com.pvnetwork.core.profile.SecretRef
import com.pvnetwork.core.security.SecretPurpose
import com.pvnetwork.core.security.SecretStore
import com.pvnetwork.core.security.clearSecret
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalEncodingApi::class)
class ShareLinksTest {

    private class MemoryStore : SecretStore {
        val values = linkedMapOf<String, CharArray>()
        private var next = 1
        override fun put(purpose: SecretPurpose, secret: CharArray): SecretRef =
            SecretRef("secret://t/${next++}").also { values[it.value] = secret.copyOf() }
        override fun <T> withSecret(ref: SecretRef, block: (CharArray) -> T): T? =
            values[ref.value]?.let { stored -> block(stored.copyOf()) }
        override fun delete(ref: SecretRef): Boolean = values.remove(ref.value) != null
        fun clear() = values.values.forEach { it.clearSecret() }
    }

    private fun b64(text: String): String = Base64.Default.encode(text.toByteArray())

    @Test
    fun vmessLinkParsesIntoCanonicalProfile() {
        val store = MemoryStore()
        val json = """{"v":"2","ps":"Tokyo-01","add":"203.0.113.5","port":"443","id":"11111111-1111-4111-8111-111111111111","aid":"0","scy":"auto","net":"ws","type":"none","host":"cdn.example","path":"/ws","tls":"tls","sni":"cdn.example","fp":"chrome"}"""
        val result = ShareLinkParser.parse("vmess://" + b64(json), com.pvnetwork.core.profile.ProfileId("p1"), store)
        assertNotNull(result)
        val profile = result.profile
        assertEquals("Tokyo-01", profile.displayName)
        assertEquals("vmess", profile.protocolId)
        assertEquals("203.0.113.5", profile.endpoint.host)
        assertEquals(443, profile.endpoint.port)
        assertEquals("websocket", profile.extensions["xray.transport"])
        assertEquals("tls", profile.extensions["xray.security"])
        assertEquals("cdn.example", profile.extensions["xray.server-name"])
        assertEquals("/ws", profile.extensions["xray.path"])
        assertEquals("11111111-1111-4111-8111-111111111111", store.withSecret(profile.secretRefs.getValue("xray.vmess.identity")) { String(it) })
        assertTrue(profile.secretRefs.containsKey("xray.original-share-link"))
    }

    @Test
    fun trojanLinkParsesWithDefaults() {
        val store = MemoryStore()
        val result = ShareLinkParser.parse(
            "trojan://pass%40word@203.0.113.9:8443?security=tls&sni=example.invalid#TR%20Server",
            com.pvnetwork.core.profile.ProfileId("p2"),
            store,
        )
        assertNotNull(result)
        val profile = result.profile
        assertEquals("TR Server", profile.displayName)
        assertEquals("trojan", profile.protocolId)
        assertEquals("example.invalid", profile.extensions["xray.server-name"])
        assertEquals("pass@word", store.withSecret(profile.secretRefs.getValue("xray.trojan.password")) { String(it) })
    }

    @Test
    fun shadowsocksSip002LinkParses() {
        val store = MemoryStore()
        val userinfo = b64("aes-256-gcm:test-password")
        val result = ShareLinkParser.parse(
            "ss://$userinfo@203.0.113.7:8388#SS%20Node",
            com.pvnetwork.core.profile.ProfileId("p3"),
            store,
        )
        assertNotNull(result)
        val profile = result.profile
        assertEquals("SS Node", profile.displayName)
        assertEquals("aes-256-gcm", profile.extensions["xray.shadowsocks-method"])
        assertEquals("test-password", store.withSecret(profile.secretRefs.getValue("xray.shadowsocks.password")) { String(it) })
    }

    @Test
    fun unsupportedSchemeIsRejectedWithoutSideEffects() {
        val store = MemoryStore()
        assertNull(ShareLinkParser.parse("https://example.com/sub", com.pvnetwork.core.profile.ProfileId("p4"), store))
        assertNull(ShareLinkParser.parse("hysteria2://x@h:443", com.pvnetwork.core.profile.ProfileId("p5"), store))
    }

    @Test
    fun malformedLinksFailClosed() {
        val store = MemoryStore()
        assertFailsWith<ShareLinkException> {
            ShareLinkParser.parse("vmess://" + b64("not json"), com.pvnetwork.core.profile.ProfileId("p6"), store)
        }
        assertFailsWith<ShareLinkException> {
            ShareLinkParser.parse("trojan://@203.0.113.1:443", com.pvnetwork.core.profile.ProfileId("p7"), store)
        }
        assertFailsWith<ShareLinkException> {
            ShareLinkParser.parse("ss://", com.pvnetwork.core.profile.ProfileId("p8"), store)
        }
    }

    @Test
    fun lenientBase64HandlesUrlSafeAndMissingPadding() {
        val text = "hello world"
        val standard = Base64.Default.encode(text.toByteArray())
        val urlSafe = standard.replace('+', '-').replace('/', '_').trimEnd('=')
        assertEquals(text, decodeBase64Lenient(urlSafe))
    }
}
