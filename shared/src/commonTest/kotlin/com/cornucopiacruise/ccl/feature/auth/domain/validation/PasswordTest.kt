package com.cornucopiacruise.ccl.feature.auth.domain.validation

import kotlin.test.Test
import kotlin.test.assertEquals

class PasswordTest {

    @Test
    fun `empty or blank password returns IS_BLANK`() {
        assertEquals(PasswordError.IS_BLANK, validatePassword(" "))
        assertEquals(PasswordError.IS_BLANK, validatePassword(""))
    }

    @Test
    fun `password with no digits returns NO_DIGITS`() {
        assertEquals(PasswordError.NO_DIGITS, validatePassword("password!!!"))
    }

    @Test
    fun `password with no special characters returns NO_SPECIALS`() {
        assertEquals(PasswordError.NO_SPECIALS, validatePassword("password123"))
    }

    @Test
    fun `password with no letters returns NO_LETTERS`() {
        assertEquals(PasswordError.NO_LETTERS, validatePassword("!!!1234"))
        assertEquals(PasswordError.NO_LETTERS, validatePassword("1234!!!"))
    }

    @Test
    fun `password with less than 8 characters returns TOO_SHORT`() {
        assertEquals(PasswordError.TOO_SHORT, validatePassword("123abc!"))
    }

    @Test
    fun `password with more than 30 characters returns TOO_LONG`() {
        assertEquals(
            PasswordError.TOO_LONG, validatePassword(
            "a".repeat(10) + "1".repeat(10) + "#".repeat(11)
            )
        )
    }

    @Test
    fun `valid password returns null`() {
        assertEquals(null, validatePassword("password123!"))
    }
}