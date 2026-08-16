package com.pvnetwork.core.importing

import com.pvnetwork.core.profile.PVProfile

enum class ImportSourceKind {
    SHARE_LINK,
    FILE,
    TEXT,
    QR,
    SUBSCRIPTION,
}

data class ImportInput(
    val kind: ImportSourceKind,
    val sourceLabel: String? = null,
    val payload: String,
)

enum class ImportWarningKind {
    UNSUPPORTED_FIELD,
    LOSSY_CONVERSION,
    AMBIGUOUS_SEMANTICS,
    VERSION_MISMATCH,
    DUPLICATE_CANDIDATE,
}

data class ImportWarning(
    val kind: ImportWarningKind,
    val field: String? = null,
    val message: String,
)

data class ImportCandidate(
    val profile: PVProfile,
    val warnings: List<ImportWarning> = emptyList(),
    val preservedSource: String? = null,
) {
    val isLossy: Boolean
        get() = warnings.any { it.kind == ImportWarningKind.LOSSY_CONVERSION }
}

interface ProfileImporter {
    val importerId: String
    fun confidence(input: ImportInput): Int
    fun parse(input: ImportInput): List<ImportCandidate>
}
