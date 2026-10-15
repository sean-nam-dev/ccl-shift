package com.cornucopiacruise.ccl.feature.auth.domain.validation

import com.cornucopiacruise.ccl.core.domain.hasDigits
import com.cornucopiacruise.ccl.core.domain.hasSpecialChars

enum class FullNameError {
    IS_BLANK,
    HAS_NUMBERS,
    HAS_SPECIAL_CHARACTERS,
    TOO_SHORT,
    TOO_LONG
}

const val FULL_NAME_MIN_LENGTH = 3
const val FULL_NAME_MAX_LENGTH = 20

fun validateFullName(input: String): FullNameError? {
    return when {
        input.isBlank() -> FullNameError.IS_BLANK
        input.hasDigits() -> FullNameError.HAS_NUMBERS
        input.hasSpecialChars() -> FullNameError.HAS_SPECIAL_CHARACTERS
        input.length < FULL_NAME_MIN_LENGTH -> FullNameError.TOO_SHORT
        input.length > FULL_NAME_MAX_LENGTH -> FullNameError.TOO_LONG
        else -> null
    }
}