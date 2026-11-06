package com.cornucopiacruise.ccl.feature.auth.presentation.register

import app.cash.turbine.test
import co.touchlab.kermit.Logger
import co.touchlab.kermit.loggerConfigInit
import com.cornucopiacruise.ccl.feature.auth.domain.repository.AuthError
import com.cornucopiacruise.ccl.feature.auth.domain.repository.AuthRepository
import com.cornucopiacruise.ccl.feature.auth.domain.repository.AuthResult
import com.cornucopiacruise.ccl.feature.auth.domain.repository.FakeAuthRepository
import com.cornucopiacruise.ccl.feature.auth.domain.usecase.LoginUseCase
import com.cornucopiacruise.ccl.feature.auth.domain.usecase.LoginUseCaseTest
import com.cornucopiacruise.ccl.feature.auth.presentation.login.LoginEffect
import com.cornucopiacruise.ccl.feature.auth.presentation.login.LoginEvent
import com.cornucopiacruise.ccl.feature.auth.presentation.login.LoginViewModel
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
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createViewModel(
        repository: AuthRepository = FakeAuthRepository()
    ) = LoginViewModel(Logger, LoginUseCase(repository))

    private fun LoginViewModel.fillValidForm(
         email: String = "test@example.com",
         password: String = "password"
    ) {
        onEvent(LoginEvent.EmailChange(email))
        onEvent(LoginEvent.PasswordChange(password))
    }

    @Test
    fun `initial state has empty fields with false errors and hidden password`() {
        val viewModel = createViewModel()

        assertEquals("", viewModel.state.value.email)
        assertFalse(viewModel.state.value.isEmailError)
        assertEquals("", viewModel.state.value.password)
        assertFalse(viewModel.state.value.isPasswordError)
        assertFalse(viewModel.state.value.isPasswordVisible)
    }

    @Test
    fun `EmailChange event updates state and resets error to false`() {
        val viewModel = createViewModel()
        val newEmail = "test@example.com"

        viewModel.onEvent(LoginEvent.EmailChange(newEmail))

        assertEquals(newEmail, viewModel.state.value.email)
        assertFalse(viewModel.state.value.isEmailError)
    }

    @Test
    fun `ClearEmail event clears email field`() {
        val viewModel = createViewModel()
        val newEmail = "test@example.com"

        viewModel.onEvent(LoginEvent.EmailChange(newEmail))
        viewModel.onEvent(LoginEvent.ClearEmail)

        assertEquals("", viewModel.state.value.email)
    }

    @Test
    fun `PasswordChange event updates state and resets error to false`() {
        val viewModel = createViewModel()
        val newPassword = "password"

        viewModel.onEvent(LoginEvent.PasswordChange(newPassword))

        assertEquals(newPassword, viewModel.state.value.password)
        assertFalse(viewModel.state.value.isPasswordError)
    }

    @Test
    fun `TogglePasswordVisibility event updates state`() {
        val viewModel = createViewModel()

        viewModel.onEvent(LoginEvent.TogglePasswordVisibility)
        assertTrue(viewModel.state.value.isPasswordVisible)

        viewModel.onEvent(LoginEvent.TogglePasswordVisibility)
        assertFalse(viewModel.state.value.isPasswordVisible)
    }

    @Test
    fun `login click on empty form sets errors and does not call repository`() {
        val repository = FakeAuthRepository()
        val viewModel = createViewModel(repository)

        viewModel.onEvent(LoginEvent.LogInClick)

        assertTrue(viewModel.state.value.isEmailError)
        assertTrue(viewModel.state.value.isPasswordError)
        assertTrue(repository.loginCalls.isEmpty())
    }

    @Test
    fun `simple password is accepted on login`() {
        val repository = FakeAuthRepository()
        val viewModel = createViewModel(repository).apply { fillValidForm() }

        viewModel.onEvent(LoginEvent.LogInClick)

        assertEquals(1, repository.loginCalls.size)
    }

    @Test
    fun `login trims email but keeps password as is`() {
        val repository = FakeAuthRepository()
        val viewModel = createViewModel(repository)
        val email = " test@example.com "
        val password = " password "

        viewModel.onEvent(LoginEvent.EmailChange(email))
        viewModel.onEvent(LoginEvent.PasswordChange(password))

        viewModel.onEvent(LoginEvent.LogInClick)

        assertEquals(
            listOf(FakeAuthRepository.LoginCall(email = email.trim(), password = password)),
            repository.loginCalls
        )
    }

    @Test
    fun `is loading while login in progress and false after`() {
        val repository = FakeAuthRepository()
        val gate = CompletableDeferred<Unit>()
        repository.loginGate = gate
        val viewModel = createViewModel(repository).apply { fillValidForm() }

        viewModel.onEvent(LoginEvent.LogInClick)
        assertTrue(viewModel.state.value.isLoading)

        gate.complete(Unit)
        assertFalse(viewModel.state.value.isLoading)
    }

    @Test
    fun `second click while loading does not call repository again`() {
        val repository = FakeAuthRepository()
        repository.loginGate = CompletableDeferred()
        val viewModel = createViewModel(repository).apply { fillValidForm() }

        viewModel.onEvent(LoginEvent.LogInClick)
        viewModel.onEvent(LoginEvent.LogInClick)

        assertEquals(1, repository.loginCalls.size)
    }

    @Test
    fun `successful login emits navigate to home`() = runTest {
        val viewModel = createViewModel().apply { fillValidForm() }

        viewModel.effects.test {
            viewModel.onEvent(LoginEvent.LogInClick)
            assertEquals(LoginEffect.NavigateToHome, awaitItem())
        }
    }

    @Test
    fun `failed login emits show error and stops loading`() = runTest {
        AuthError.entries.forEach { error ->
            val repository = FakeAuthRepository().apply {
                loginResult = AuthResult.Failure(error)
            }

            val viewModel = createViewModel(repository).apply { fillValidForm() }

            viewModel.effects.test {
                viewModel.onEvent(LoginEvent.LogInClick)
                assertEquals(LoginEffect.ShowAuthError(error), awaitItem())
            }

            assertFalse(viewModel.state.value.isLoading)
        }
    }

    @Test
    fun `register click emits navigate to register screen`() = runTest {
        val viewModel = createViewModel()

        viewModel.effects.test {
            viewModel.onEvent(LoginEvent.RegisterClick)
            assertEquals(LoginEffect.NavigateToRegister, awaitItem())
        }
    }
}