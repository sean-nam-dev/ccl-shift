package com.cornucopiacruise.ccl.feature.auth.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cornucopiacruise.ccl.feature.auth.domain.repository.AuthRepository
import com.cornucopiacruise.ccl.feature.auth.domain.validation.validateEmail
import com.cornucopiacruise.ccl.feature.auth.domain.validation.validateFullName
import com.cornucopiacruise.ccl.feature.auth.domain.validation.validatePassword
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _state = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> = _state.asStateFlow()

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
            is RegisterEvent.RegisterClick -> {
                if (hasErrors() || _state.value.isLoading) {
                    return
                } else {
                    viewModelScope.launch {
                        _state.update { it.copy(isLoading = true) }

                        authRepository.register(
                            _state.value.fullName,
                            _state.value.email,
                            _state.value.password
                        )

                        _state.update { it.copy(isLoading = false) }
                    }
                }
            }
            is RegisterEvent.LogInClick -> {

            }
        }
    }

    private fun hasErrors(): Boolean {
        val trimmedFullName = state.value.fullName.trim()
        val trimmedEmail = state.value.email.trim()

        val fullNameResult = validateFullName(trimmedFullName)
        val emailResult = validateEmail(trimmedEmail)
        val passwordResult = validatePassword(state.value.password)

        _state.update {
            it.copy(
                fullName = trimmedFullName,
                email = trimmedEmail,
                fullNameError = fullNameResult,
                emailError = emailResult,
                passwordError = passwordResult,
            )
        }

        return fullNameResult != null || emailResult != null || passwordResult != null
    }
}



