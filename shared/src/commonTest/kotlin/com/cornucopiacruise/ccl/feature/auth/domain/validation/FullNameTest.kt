package com.cornucopiacruise.ccl.feature.auth.domain.validation

import kotlin.test.Test
import kotlin.test.assertEquals

class FullNameTest {

    @Test
    fun `empty or blank fullName returns IS_BLANK`() {
        assertEquals(FullNameError.IS_BLANK, validateFullName(" "))
        assertEquals(FullNameError.IS_BLANK, validateFullName(""))
    }

    @Test
    fun `fullName with digits returns HAS_NUMBERS`() {
        assertEquals(FullNameError.HAS_NUMBERS, validateFullName("John123"))
        assertEquals(FullNameError.HAS_NUMBERS, validateFullName("123John"))
        assertEquals(FullNameError.HAS_NUMBERS, validateFullName("Jo123hn"))
        assertEquals(FullNameError.HAS_NUMBERS, validateFullName("0"))
    }

    @Test
    fun `fullName with special characters returns HAS_SPECIAL_CHARACTERS`() {
        assertEquals(FullNameError.HAS_SPECIAL_CHARACTERS, validateFullName("John!Doe"))
        assertEquals(FullNameError.HAS_SPECIAL_CHARACTERS, validateFullName("John !Doe"))
        assertEquals(FullNameError.HAS_SPECIAL_CHARACTERS, validateFullName("John Doe!"))
        assertEquals(FullNameError.HAS_SPECIAL_CHARACTERS, validateFullName("!John Doe"))
    }

    @Test
    fun `fullName with 3 characters returns TOO_SHORT`() {
        assertEquals(FullNameError.TOO_SHORT, validateFullName("Jo"))
        assertEquals(FullNameError.TOO_SHORT, validateFullName("J"))
    }

    @Test
    fun `fullName with 20 characters returns TOO_LONG`() {
        assertEquals(FullNameError.TOO_LONG, validateFullName("JohnDoeJohnDoeJohnDoe"))
    }

    @Test
    fun `valid fullName returns null`() {
        assertEquals(null, validateFullName("John Doe"))
    }
}