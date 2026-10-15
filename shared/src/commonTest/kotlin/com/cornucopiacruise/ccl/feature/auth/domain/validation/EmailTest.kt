package com.cornucopiacruise.ccl.feature.auth.domain.validation

import kotlin.test.Test
import kotlin.test.assertEquals

class EmailTest {

    @Test
    fun `empty or blank email returns IS_BLANK`() {
        assertEquals(EmailError.IS_BLANK, validateEmail(" "))
        assertEquals(EmailError.IS_BLANK, validateEmail(""))
    }

    @Test
    fun `invalid email returns INVALID_FORMAT`() {
        assertEquals(EmailError.INVALID_FORMAT, validateEmail("invalid-email"))
        assertEquals(EmailError.INVALID_FORMAT, validateEmail("invalid@email"))
        assertEquals(EmailError.INVALID_FORMAT, validateEmail("invalid@email."))
        assertEquals(EmailError.INVALID_FORMAT, validateEmail("invalid.email.com"))
    }

    @Test
    fun `valid email returns null`() {
        assertEquals(null, validateEmail("john.doe@examplepetstore.com"))
    }
}