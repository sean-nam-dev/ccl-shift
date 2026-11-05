package com.cornucopiacruise.ccl.feature.auth.domain.repository

interface AuthRepository {
    suspend fun register(fullName: String, email: String, password: String): AuthResult
    suspend fun login(email: String, password: String): AuthResult
}

sealed class AuthResult {

    data object Success : AuthResult()

    data class Failure(val reason: AuthError) : AuthResult()
}

enum class AuthError {
    EMAIL_ALREADY_IN_USE,
    INVALID_CREDENTIALS,
    NETWORK,
    UNKNOWN
}
