package com.pvnetwork.core

import com.pvnetwork.core.branding.PVNetworkBrand
import com.pvnetwork.core.i18n.PVLocales
import com.pvnetwork.core.i18n.TextDirection
import com.pvnetwork.core.i18n.TextKind
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class LocalizationBrandingTest {
    @Test
    fun englishAndPersianAreFirstClassLocales() {
        assertEquals(PVLocales.ENGLISH, PVLocales.resolve("en-US"))
        assertEquals(PVLocales.PERSIAN, PVLocales.resolve("fa-IR"))
        assertEquals(TextDirection.LTR, PVLocales.ENGLISH.direction)
        assertEquals(TextDirection.RTL, PVLocales.PERSIAN.direction)
        assertNull(PVLocales.resolve("de-DE"))
    }

    @Test
    fun technicalTokensRemainLtrInsidePersianLayouts() {
        assertEquals(TextDirection.LTR, PVLocales.effectiveDirection(PVLocales.PERSIAN, TextKind.TECHNICAL_TOKEN))
        assertEquals(TextDirection.RTL, PVLocales.effectiveDirection(PVLocales.PERSIAN, TextKind.NATURAL_LANGUAGE))
    }

    @Test
    fun brandingUsesOnlyRepositoryConfirmedProductName() {
        assertEquals("PVNetwork", PVNetworkBrand.identity.productName)
        assertEquals("PVNetwork", PVNetworkBrand.identity.shortName)
    }
}
