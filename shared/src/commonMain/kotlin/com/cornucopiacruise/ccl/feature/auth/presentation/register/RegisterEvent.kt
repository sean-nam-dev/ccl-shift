package com.cornucopiacruise.ccl.feature.auth.presentation.register

sealed interface RegisterEvent {
    data class FullNameChange(val newName: String): RegisterEvent
    data class EmailChange(val newEmail: String): RegisterEvent
    data class PasswordChange(val newPassword: String): RegisterEvent
    data object TogglePasswordVisibility : RegisterEvent
    data object RegisterClick : RegisterEvent
    data object LogInClick : RegisterEvent
}