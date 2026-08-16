package com.pvnetwork.core.i18n

enum class TextDirection {
    LTR,
    RTL,
}

data class SupportedLocale(
    val languageTag: String,
    val direction: TextDirection,
) {
    init {
        require(languageTag.isNotBlank()) { "language tag must not be blank" }
    }
}

enum class TextKind {
    NATURAL_LANGUAGE,
    TECHNICAL_TOKEN,
}

object PVLocales {
    val ENGLISH = SupportedLocale("en", TextDirection.LTR)
    val PERSIAN = SupportedLocale("fa", TextDirection.RTL)

    val all: List<SupportedLocale> = listOf(ENGLISH, PERSIAN)

    fun resolve(languageTag: String): SupportedLocale? {
        val normalized = languageTag.trim().lowercase().substringBefore('-')
        return all.firstOrNull { it.languageTag == normalized }
    }

    fun effectiveDirection(locale: SupportedLocale, kind: TextKind): TextDirection =
        if (kind == TextKind.TECHNICAL_TOKEN) TextDirection.LTR else locale.direction
}
