package com.cornucopiacruise.ccl.feature.auth.presentation.register

import app.cash.turbine.test
import com.cornucopiacruise.ccl.feature.auth.domain.repository.AuthRepository
import com.cornucopiacruise.ccl.feature.auth.domain.repository.FakeAuthRepository
import com.cornucopiacruise.ccl.feature.auth.domain.repository.RegisterError
import com.cornucopiacruise.ccl.feature.auth.domain.repository.RegisterResult
import com.cornucopiacruise.ccl.feature.auth.domain.validation.EmailError
import com.cornucopiacruise.ccl.feature.auth.domain.validation.FullNameError
import com.cornucopiacruise.ccl.feature.auth.domain.validation.PasswordError
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class RegisterViewModelTest {

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createViewModel(
        repository: AuthRepository = FakeAuthRepository()
    ) = RegisterViewModel(repository)

    private fun RegisterViewModel.fillValidForm(
        newName: String = "John Doe",
        newEmail: String = "test@example.com",
        newPassword: String = "password123!!!"
    ) {
        onEvent(RegisterEvent.FullNameChange(newName))
        onEvent(RegisterEvent.EmailChange(newEmail))
        onEvent(RegisterEvent.PasswordChange(newPassword))
    }

    @Test
    fun `initial state has empty fields with null errors and hidden password`() {
        val viewModel = createViewModel()

        val state = viewModel.state.value

        assertEquals("", state.fullName)
        assertNull(state.fullNameError)
        assertEquals("", state.email)
        assertNull(state.emailError)
        assertEquals("", state.password)
        assertNull(state.passwordError)
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

    @Test
    fun `RegisterClick with valid form calls repository`() {
        val repository = FakeAuthRepository()
        val viewModel = createViewModel(repository).apply { fillValidForm() }

        viewModel.onEvent(RegisterEvent.RegisterClick)

        assertEquals(
            listOf(
                FakeAuthRepository.Call(
                    "John Doe",
                    "test@example.com",
                    "password123!!!"
                )
            ),
            repository.registerCallList
        )
    }

    @Test
    fun `loading is true while registration in progress and false after`() {
        val repository = FakeAuthRepository()
        val gate = CompletableDeferred<Unit>()
        repository.registerGate = gate
        val viewModel = createViewModel(repository).apply { fillValidForm() }

        viewModel.onEvent(RegisterEvent.RegisterClick)
        assertTrue(viewModel.state.value.isLoading)

        gate.complete(Unit)
        assertFalse(viewModel.state.value.isLoading)
    }

    @Test
    fun `double click on register button has no effect`() {
        val repository = FakeAuthRepository()
        repository.registerGate = CompletableDeferred()
        val viewModel = createViewModel(repository).apply { fillValidForm() }

        viewModel.onEvent(RegisterEvent.RegisterClick)
        viewModel.onEvent(RegisterEvent.RegisterClick)

        assertEquals(1, repository.registerCallList.size)
    }

    @Test
    fun `login click emits navigate to login effect`() = runTest {
        val viewModel = createViewModel()

        viewModel.effects.test {
            viewModel.onEvent(RegisterEvent.LogInClick)

            assertEquals(RegisterEffect.NavigateToLogin, awaitItem())
        }
    }

    @Test
    fun `successful registration emits navigate to home effect`() = runTest {
        val viewModel = createViewModel().apply { fillValidForm() }

        viewModel.effects.test {
            viewModel.onEvent(RegisterEvent.RegisterClick)

            assertEquals(RegisterEffect.NavigateToHome, awaitItem())
        }
    }

    @Test
    fun `email already in use failure sets email error and stops loading and triggers show error effect`() = runTest {
        val repository = FakeAuthRepository().apply {
            registerResult = RegisterResult.Failure(RegisterError.EMAIL_ALREADY_IN_USE)
        }
        val viewModel = createViewModel(repository).apply {
            fillValidForm(newEmail = "test@example.com")
        }

        viewModel.onEvent(RegisterEvent.RegisterClick)

        assertEquals(EmailError.ALREADY_IN_USE, viewModel.state.value.emailError)
        assertFalse(viewModel.state.value.isLoading)

        viewModel.effects.test {
            assertEquals(RegisterEffect.ShowRegisterError(RegisterError.EMAIL_ALREADY_IN_USE), awaitItem())
        }
    }

    @Test
    fun `network failure sets show error effect and stops loading`() = runTest {
        val repository = FakeAuthRepository().apply {
            registerResult = RegisterResult.Failure(RegisterError.NETWORK)
        }
        val viewModel = createViewModel(repository).apply {
            fillValidForm()
        }

        viewModel.onEvent(RegisterEvent.RegisterClick)

        assertFalse(viewModel.state.value.isLoading)

        viewModel.effects.test {
            assertEquals(RegisterEffect.ShowRegisterError(RegisterError.NETWORK), awaitItem())
        }
    }

    @Test
    fun `unknown failure sets show error effect and stops loading`() = runTest {
        val repository = FakeAuthRepository().apply {
            registerResult = RegisterResult.Failure(RegisterError.UNKNOWN)
        }
        val viewModel = createViewModel(repository).apply {
            fillValidForm()
        }

        viewModel.onEvent(RegisterEvent.RegisterClick)

        assertFalse(viewModel.state.value.isLoading)

        viewModel.effects.test {
            assertEquals(RegisterEffect.ShowRegisterError(RegisterError.UNKNOWN), awaitItem())
        }
    }
}