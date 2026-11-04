package com.cornucopiacruise.ccl.feature.auth.presentation.login

sealed interface LoginEvent {

    data class EmailChange(val newEmail: String) : LoginEvent

    data object ClearEmail : LoginEvent

    data class PasswordChange(val newPassword: String) : LoginEvent

    data object TogglePasswordVisibility : LoginEvent

    data object RegisterClick : LoginEvent

    data object LogInClick : LoginEvent
}