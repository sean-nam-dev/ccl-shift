package com.cornucopiacruise.ccl.feature.auth.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.cornucopiacruise.ccl.feature.auth.domain.repository.AuthError
import com.cornucopiacruise.ccl.feature.auth.domain.repository.AuthResult
import com.cornucopiacruise.ccl.feature.auth.domain.usecase.RegisterUseCase
import com.cornucopiacruise.ccl.core.domain.EmailError
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    logger: Logger,
    private val registerUseCase: RegisterUseCase
) : ViewModel() {
    private val log = logger.withTag("RegisterViewModel")
    private val _state = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> = _state.asStateFlow()

    private val _effects = Channel<RegisterEffect>(Channel.BUFFERED)
    val effects = _effects.receiveAsFlow()

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.FullNameChange ->
                _state.update { it.copy(fullName = event.newName, fullNameError = null) }
            is RegisterEvent.ClearFullName ->
                _state.update { it.copy(fullName = "", fullNameError = null) }
            is RegisterEvent.EmailChange ->
                _state.update { it.copy(email = event.newEmail, emailError = null) }
            is RegisterEvent.ClearEmail ->
                _state.update { it.copy(email = "", emailError = null) }
            is RegisterEvent.PasswordChange ->
                _state.update { it.copy(password = event.newPassword, passwordError = null) }
            is RegisterEvent.TogglePasswordVisibility ->
                _state.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
            is RegisterEvent.RegisterClick ->
                register()
            is RegisterEvent.LogInClick ->
                _effects.trySend(RegisterEffect.NavigateToLogin)
        }
    }

    private fun register() {
        log.d { "Register action received" }

        if (_state.value.isLoading) {
            log.d { "Loading" }
            return
        }

        _state.update { it.validated() }
        if (_state.value.hasErrors) {
            log.d { "Register blocked by validation" }
            return
        }

        log.i { "Loading started" }
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val result = registerUseCase(
                fullName = _state.value.fullName,
                email = _state.value.email,
                password = _state.value.password
            )

            _state.update { it.copy(isLoading = false) }

            when (result) {
                is AuthResult.Success -> {
                    log.i { "Success" }
                    _effects.trySend(RegisterEffect.NavigateToHome)
                }
                is AuthResult.Failure -> {
                    log.w { "Failed with ${result.reason}" }
                    handleFailure(result.reason)
                }
            }
        }
    }

    private fun handleFailure(failure: AuthError) {
        if (failure == AuthError.EMAIL_ALREADY_IN_USE) {
            _state.update { it.copy(emailError = EmailError.ALREADY_IN_USE) }
        }
        _effects.trySend(RegisterEffect.ShowAuthError(failure))
    }
}