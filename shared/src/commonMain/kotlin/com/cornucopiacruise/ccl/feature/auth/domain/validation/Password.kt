package com.cornucopiacruise.ccl.feature.auth.domain.validation

import com.cornucopiacruise.ccl.core.domain.hasDigits
import com.cornucopiacruise.ccl.core.domain.hasLetters
import com.cornucopiacruise.ccl.core.domain.hasSpecialChars

enum class PasswordError {
    IS_BLANK,
    NO_DIGITS,
    NO_SPECIALS,
    NO_LETTERS,
    TOO_SHORT,
    TOO_LONG
}

const val PASSWORD_MIN_LENGTH = 8
const val PASSWORD_MAX_LENGTH = 30

fun validatePassword(input: String): PasswordError? {
    return when {
        input.isBlank() -> PasswordError.IS_BLANK
        !input.hasDigits() -> PasswordError.NO_DIGITS
        !input.hasSpecialChars() -> PasswordError.NO_SPECIALS
        !input.hasLetters() -> PasswordError.NO_LETTERS
        input.length < PASSWORD_MIN_LENGTH -> PasswordError.TOO_SHORT
        input.length > PASSWORD_MAX_LENGTH -> PasswordError.TOO_LONG
        else -> null
    }
}