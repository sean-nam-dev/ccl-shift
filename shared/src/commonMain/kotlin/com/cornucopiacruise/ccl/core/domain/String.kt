package com.cornucopiacruise.ccl.core.domain

fun String.hasSpecialChars(): Boolean {
    return this.any { !it.isLetterOrDigit() && !it.isWhitespace() }
}

fun String.hasDigits(): Boolean {
    return this.any { it.isDigit() }
}

fun String.hasLetters(): Boolean {
    return this.any { it.isLetter() }
}