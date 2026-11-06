package com.cornucopiacruise.ccl.feature.auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.cornucopiacruise.ccl.feature.auth.domain.repository.AuthResult
import com.cornucopiacruise.ccl.feature.auth.domain.usecase.LoginUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    logger: Logger,
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    private val log = logger.withTag("LoginViewModel")
    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    private val _effects = Channel<LoginEffect>(Channel.BUFFERED)
    val effects = _effects.receiveAsFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EmailChange ->
                _state.update { it.copy(email = event.newEmail) }
            is LoginEvent.ClearEmail ->
                _state.value = _state.value.copy(email = "")
            is LoginEvent.PasswordChange ->
                _state.update { it.copy(password = event.newPassword) }
            is LoginEvent.TogglePasswordVisibility ->
                _state.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
            is LoginEvent.LogInClick ->
                login()
            is LoginEvent.RegisterClick -> _effects.trySend(LoginEffect.NavigateToRegister)
        }
    }

    private fun login() {
        log.d { "Login action received" }

        if (_state.value.isLoading) {
            log.d { "Loading" }
            return
        }

        _state.update { it.validated() }
        if (_state.value.hasErrors) {
            log.d { "Login blocked by validation" }
            return
        }

        log.i { "Loading started" }
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val result = loginUseCase(
                email = _state.value.email,
                password = _state.value.password
            )

            _state.update { it.copy(isLoading = false) }

            when (result) {
                is AuthResult.Success -> {
                    log.i { "Success" }
                    _effects.trySend(LoginEffect.NavigateToHome)
                }
                is AuthResult.Failure -> {
                    log.w { "Failed with ${result.reason}" }
                    _effects.trySend(LoginEffect.ShowAuthError(result.reason))
                }
            }
        }
    }
}