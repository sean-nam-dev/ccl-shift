package com.cornucopiacruise.ccl.feature.auth.domain.repository.register

sealed class RegisterResult {
    data object Success : RegisterResult()
    data class Failure(val reason: RegisterError) : RegisterResult()
}