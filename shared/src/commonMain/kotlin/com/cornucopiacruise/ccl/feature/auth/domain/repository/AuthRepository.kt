package com.cornucopiacruise.ccl.feature.auth.domain.repository

interface AuthRepository {
    suspend fun register(fullName: String, email: String, password: String): RegisterResult
}

sealed class RegisterResult {
    data object Success : RegisterResult()
    data class Failure(val reason: RegisterError) : RegisterResult()
}

enum class RegisterError {
    ALREADY_IN_USE,
    NETWORK,
    UNKNOWN
}