package com.pvnetwork.core.entitlement

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class EntitlementContractTest {

    private fun active(
        expiresAtEpochSeconds: Long? = null,
        trafficQuotaBytes: Long? = null,
        trafficUsedBytes: Long? = null,
        deviceLimit: Int? = null,
    ) = Entitlement(
        planId = "plan-1",
        status = EntitlementStatus.ACTIVE,
        expiresAtEpochSeconds = expiresAtEpochSeconds,
        trafficQuotaBytes = trafficQuotaBytes,
        trafficUsedBytes = trafficUsedBytes,
        deviceLimit = deviceLimit,
        features = setOf("feature.a"),
    )

    @Test
    fun unlimitedQuotaIsRepresentedByNull() {
        val e = active(trafficQuotaBytes = null, trafficUsedBytes = null)
        assertNull(e.trafficQuotaBytes)
        assertNull(e.remainingTrafficBytes)
        assertTrue(e.hasUnlimitedTraffic)
    }

    @Test
    fun finiteQuotaComputesRemainingTraffic() {
        val e = active(trafficQuotaBytes = 1_000L, trafficUsedBytes = 250L)
        assertEquals(750L, e.remainingTrafficBytes)
        assertFalse(e.hasUnlimitedTraffic)
    }

    @Test
    fun expiredEntitlementIsNotActiveAtOrAfterExpiry() {
        val e = active(expiresAtEpochSeconds = 5_000L)
        assertTrue(e.isActiveAt(4_999L))
        assertFalse(e.isActiveAt(5_000L))
    }

    @Test
    fun revokedOrExpiredStatusIsNeverActive() {
        listOf(EntitlementStatus.EXPIRED, EntitlementStatus.REVOKED).forEach { status ->
            val e = Entitlement(
                planId = "plan-1",
                status = status,
                expiresAtEpochSeconds = null,
                trafficQuotaBytes = null,
                trafficUsedBytes = null,
                deviceLimit = null,
                features = emptySet(),
            )
            assertFalse(e.isActiveAt(0L))
        }
    }

    @Test
    fun negativeQuotaIsRejected() {
        assertFailsWith<IllegalArgumentException> { active(trafficQuotaBytes = -1L) }
    }

    @Test
    fun negativeUsedTrafficIsRejected() {
        assertFailsWith<IllegalArgumentException> { active(trafficUsedBytes = -1L) }
    }

    @Test
    fun negativeDeviceLimitIsRejected() {
        assertFailsWith<IllegalArgumentException> { active(deviceLimit = -1) }
    }

    @Test
    fun blankPlanIdIsRejected() {
        assertFailsWith<IllegalArgumentException> {
            Entitlement(
                planId = "",
                status = EntitlementStatus.ACTIVE,
            )
        }
    }
}

class DeviceRegistrationContractTest {

    @Test
    fun registrationKeepsMinimalDeviceFacts() {
        val d = DeviceRegistration(
            deviceId = "device-1",
            registeredAtEpochSeconds = 100L,
        )
        assertEquals("device-1", d.deviceId)
        assertNull(d.displayName)
        assertNull(d.lastSeenAtEpochSeconds)
        assertFalse(d.revoked)
    }

    @Test
    fun blankDeviceIdIsRejected() {
        assertFailsWith<IllegalArgumentException> {
            DeviceRegistration(deviceId = "", registeredAtEpochSeconds = 100L)
        }
    }

    @Test
    fun negativeTimestampsAreRejected() {
        assertFailsWith<IllegalArgumentException> {
            DeviceRegistration(deviceId = "device-1", registeredAtEpochSeconds = -1L)
        }
    }
}
