package com.cornucopiacruise.ccl.feature.auth.presentation.register

import com.cornucopiacruise.ccl.feature.auth.domain.validation.EmailError
import com.cornucopiacruise.ccl.feature.auth.domain.validation.FullNameError
import com.cornucopiacruise.ccl.feature.auth.domain.validation.PasswordError
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class RegisterViewModelTest {

    @Test
    fun `initial state has empty fields and hidden password`() {
        val viewModel = createViewModel()

        val state = viewModel.state.value

        assertEquals("", state.fullName)
        assertEquals("", state.email)
        assertEquals("", state.password)
        assertFalse(state.isPasswordVisible)
    }

    @Test
    fun `FullNameChange event updates state`() {
        val viewModel = createViewModel()
        val newName = "John Doe"

        viewModel.onEvent(RegisterEvent.FullNameChange(newName))

        assertEquals(newName, viewModel.state.value.fullName)
    }

    @Test
    fun `EmailChange event updates state`() {
        val viewModel = createViewModel()
        val newEmail = "test@example.com"

        viewModel.onEvent(RegisterEvent.EmailChange(newEmail))

        assertEquals(newEmail, viewModel.state.value.email)
    }

    @Test
    fun `PasswordChange event updates state`() {
        val viewModel = createViewModel()
        val newPassword = "password123"

        viewModel.onEvent(RegisterEvent.PasswordChange(newPassword))

        assertEquals(newPassword, viewModel.state.value.password)
    }

    @Test
    fun `TogglePasswordVisibility event updates state`() {
        val viewModel = createViewModel()

        viewModel.onEvent(RegisterEvent.TogglePasswordVisibility)

        assertTrue(viewModel.state.value.isPasswordVisible)

        viewModel.onEvent(RegisterEvent.TogglePasswordVisibility)

        assertFalse(viewModel.state.value.isPasswordVisible)
    }

    @Test
    fun `RegisterClick event sets IS_BLANK errors on empty input fields`() {
        val viewModel = createViewModel()

        viewModel.onEvent(RegisterEvent.RegisterClick)

        val state = viewModel.state.value

        assertEquals(FullNameError.IS_BLANK, state.fullNameError)
        assertEquals(EmailError.IS_BLANK, state.emailError)
        assertEquals(PasswordError.IS_BLANK, state.passwordError)
    }

    @Test
    fun `RegisterClick event leaves no errors on valid form`() {
        val viewModel = createViewModel()

        viewModel.onEvent(RegisterEvent.FullNameChange("John Doe"))
        viewModel.onEvent(RegisterEvent.EmailChange("test@example.com"))
        viewModel.onEvent(RegisterEvent.PasswordChange("password123!!!"))

        viewModel.onEvent(RegisterEvent.RegisterClick)
    }

    @Test
    fun `RegisterClick event trims fullName and email while leaving password untouched`() {
        val viewModel = createViewModel()

        val fullName = " John Doe "
        val email = " test@example.com "
        val password = " password123!!! "

        viewModel.onEvent(RegisterEvent.FullNameChange(fullName))
        viewModel.onEvent(RegisterEvent.EmailChange(email))
        viewModel.onEvent(RegisterEvent.PasswordChange(password))

        viewModel.onEvent(RegisterEvent.RegisterClick)

        assertEquals(fullName.trim(), viewModel.state.value.fullName)
        assertEquals(email.trim(), viewModel.state.value.email)
        assertEquals(password, viewModel.state.value.password)
    }

    private fun createViewModel() = RegisterViewModel()
}