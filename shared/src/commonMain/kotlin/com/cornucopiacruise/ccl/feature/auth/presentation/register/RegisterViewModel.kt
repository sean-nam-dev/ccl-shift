package com.cornucopiacruise.ccl.feature.auth.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cornucopiacruise.ccl.feature.auth.domain.repository.AuthRepository
import com.cornucopiacruise.ccl.feature.auth.domain.repository.RegisterResult
import com.cornucopiacruise.ccl.feature.auth.presentation.register.util.hasErrors
import com.cornucopiacruise.ccl.feature.auth.presentation.register.util.validated
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _state = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> = _state.asStateFlow()

    private val _effects = Channel<RegisterEffect>(Channel.BUFFERED)
    val effects = _effects.receiveAsFlow()

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.FullNameChange ->
                _state.update { it.copy(fullName = event.newName, fullNameError = null) }
            is RegisterEvent.EmailChange ->
                _state.update { it.copy(email = event.newEmail, emailError = null) }
            is RegisterEvent.PasswordChange ->
                _state.update { it.copy(password = event.newPassword, passwordError = null) }
            is RegisterEvent.TogglePasswordVisibility ->
                _state.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
            is RegisterEvent.RegisterClick ->
                register()
            is RegisterEvent.LogInClick -> {
                _effects.trySend(RegisterEffect.NavigateToLogin)
            }
        }
    }

    private fun register() {
        if (_state.value.isLoading) return

        _state.update { it.validated() }
        if (_state.value.hasErrors) return

        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val result = authRepository.register(
                fullName = _state.value.fullName,
                email = _state.value.email,
                password = _state.value.password
            )

            _state.update { it.copy(isLoading = false) }

            when (result) {
                is RegisterResult.Success -> _effects.trySend(RegisterEffect.NavigateToHome)
                is RegisterResult.Failure -> _effects.trySend(RegisterEffect.ShowRegisterError(result.reason))
            }
        }
    }
}



