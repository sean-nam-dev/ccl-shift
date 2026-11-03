package com.cornucopiacruise.ccl.feature.auth.domain.repository.login

sealed class LoginResult {
    data object Success : LoginResult()
    data class Failure(val reason: LoginError) : LoginResult()
}