package com.pvnetwork.core.diagnostics

enum class DiagnosticSeverity {
    DEBUG,
    INFO,
    WARNING,
    ERROR,
}

data class DiagnosticEvent(
    val timestampEpochMillis: Long,
    val severity: DiagnosticSeverity,
    val subsystem: String,
    val code: String,
    val metadata: Map<String, String> = emptyMap(),
) {
    init {
        require(timestampEpochMillis >= 0) { "timestamp must be non-negative" }
        require(subsystem.isNotBlank()) { "diagnostic subsystem must not be blank" }
        require(code.isNotBlank()) { "diagnostic code must not be blank" }
    }
}

object DiagnosticSanitizer {
    const val REDACTED: String = "[REDACTED]"

    private val sensitiveFragments = setOf(
        "password",
        "passwd",
        "token",
        "privatekey",
        "private_key",
        "authorization",
        "cookie",
        "secret",
        "credential",
        "apikey",
        "api_key",
        "presharedkey",
        "pre_shared_key",
    )

    fun sanitize(metadata: Map<String, String>): Map<String, String> =
        metadata.mapValues { (key, value) ->
            if (isSensitiveKey(key)) REDACTED else value
        }

    fun sanitize(event: DiagnosticEvent): DiagnosticEvent =
        event.copy(metadata = sanitize(event.metadata))

    private fun isSensitiveKey(key: String): Boolean {
        val normalized = key.lowercase()
        return sensitiveFragments.any(normalized::contains)
    }
}
