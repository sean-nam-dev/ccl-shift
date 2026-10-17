package com.cornucopiacruise.ccl.feature.auth.domain.validation

enum class EmailError : ValidationError {
    IS_BLANK,
    INVALID_FORMAT
}

private val EMAIL = Regex("""[a-zA-Z0-9._%+-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z]{2,}""")

fun validateEmail(input: String): EmailError? {
    return when {
        input.isBlank() -> EmailError.IS_BLANK
        !EMAIL.matches(input) -> EmailError.INVALID_FORMAT
        else -> null
    }
}