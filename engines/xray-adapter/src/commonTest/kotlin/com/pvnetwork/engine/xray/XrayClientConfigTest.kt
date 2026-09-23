package com.pvnetwork.engine.xray

import com.pvnetwork.core.profile.Endpoint
import com.pvnetwork.core.profile.PVProfile
import com.pvnetwork.core.profile.ProfileId
import com.pvnetwork.core.profile.ProfileOrigin
import com.pvnetwork.core.profile.SecretRef
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class XrayClientConfigTest {

    private fun vlessProfile(
        extensions: Map<String, String> = mapOf(
            "xray.application-protocol" to "vless",
            "xray.security" to "reality",
            "xray.transport" to "raw",
            "xray.flow" to "xtls-rprx-vision",
            "xray.server-name" to "example.invalid",
            "xray.fingerprint" to "chrome",
            "xray.reality-public-key" to "pbk-value",
            "xray.reality-short-id" to "sid-value",
        ),
    ) = PVProfile(
        id = ProfileId("cfg-test"),
        displayName = "cfg",
        protocolId = XrayAdapter.VLESS_CAPABILITY,
        endpoint = Endpoint("example.invalid", 443),
        secretRefs = mapOf(XrayAdapter.VLESS_IDENTITY_SECRET_ROLE to SecretRef("secret://cfg/1")),
        extensions = extensions,
        origin = ProfileOrigin.MANUAL,
    )

    @Test
    fun buildsLocalSocksAndHttpInboundsAndVlessRealityOutbound() {
        val json = XrayClientConfig.build(
            profile = vlessProfile(),
            credential = "11111111-1111-4111-8111-111111111111".toCharArray(),
            ports = XrayClientConfig.InboundPorts(socksPort = 10808, httpPort = 10809),
        )
        assertContains(json, "\"listen\":\"127.0.0.1\"")
        assertContains(json, "\"port\":10808")
        assertContains(json, "\"port\":10809")
        assertContains(json, "\"protocol\":\"socks\"")
        assertContains(json, "\"protocol\":\"http\"")
        assertContains(json, "\"protocol\":\"vless\"")
        assertContains(json, "\"security\":\"reality\"")
        assertContains(json, "\"publicKey\":\"pbk-value\"")
        assertContains(json, "\"flow\":\"xtls-rprx-vision\"")
        assertContains(json, "\"serverName\":\"example.invalid\"")
        assertContains(json, "\"shortId\":\"sid-value\"")
        assertContains(json, "11111111-1111-4111-8111-111111111111")
        assertContains(json, "\"protocol\":\"freedom\"")
    }

    @Test
    fun websocketAndTlsSettingsAreEmitted() {
        val json = XrayClientConfig.build(
            profile = vlessProfile(
                mapOf(
                    "xray.security" to "tls",
                    "xray.transport" to "websocket",
                    "xray.path" to "/ws-path",
                    "xray.host-header" to "cdn.example",
                ),
            ),
            credential = "22222222-2222-4222-8222-222222222222".toCharArray(),
            ports = XrayClientConfig.InboundPorts(socksPort = 1080, httpPort = 1081),
        )
        assertContains(json, "\"security\":\"tls\"")
        assertContains(json, "\"network\":\"ws\"")
        assertContains(json, "\"path\":\"/ws-path\"")
        assertContains(json, "\"headers\":{\"Host\":\"cdn.example\"}")
    }

    @Test
    fun maliciousStringsAreJsonEscaped() {
        val json = XrayClientConfig.build(
            profile = PVProfile(
                id = ProfileId("escape"),
                displayName = "escape",
                protocolId = XrayAdapter.VLESS_CAPABILITY,
                endpoint = Endpoint("127.0.0.1", 8443),
                secretRefs = mapOf(XrayAdapter.VLESS_IDENTITY_SECRET_ROLE to SecretRef("secret://cfg/2")),
                extensions = mapOf(
                    "xray.security" to "tls",
                    "xray.transport" to "raw",
                    "xray.application-protocol" to "vless",
                    "xray.server-name" to "a\"b\\c\n\td",
                ),
                origin = ProfileOrigin.MANUAL,
            ),
            credential = "id\"quote".toCharArray(),
            ports = XrayClientConfig.InboundPorts(socksPort = 1080, httpPort = 1081),
        )
        assertTrue(json.contains("\\\"quote"))
        assertTrue(json.contains("a\\\"b\\\\c\\n\\td"))
        assertEquals(-1, json.indexOf("\n"), "generated config must stay single-line")
    }

    @Test
    fun inboundPortsMustBeValidAndDistinct() {
        assertFailsWith<IllegalArgumentException> {
            XrayClientConfig.InboundPorts(socksPort = 0, httpPort = 1)
        }
        assertFailsWith<IllegalArgumentException> {
            XrayClientConfig.InboundPorts(socksPort = 1080, httpPort = 1080)
        }
    }

    @Test
    fun missingRealityKeyFailsClosed() {
        val withoutKey = vlessProfile(
            mapOf(
                "xray.security" to "reality",
                "xray.transport" to "raw",
                "xray.server-name" to "example.invalid",
            ),
        )
        assertFailsWith<IllegalStateException> {
            XrayClientConfig.build(
                profile = withoutKey,
                credential = "33333333-3333-4333-8333-333333333333".toCharArray(),
                ports = XrayClientConfig.InboundPorts(socksPort = 1080, httpPort = 1081),
            )
        }
    }
}
