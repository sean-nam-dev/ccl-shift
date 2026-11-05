package com.cornucopiacruise.ccl.feature.auth.domain.repository

import kotlinx.coroutines.CompletableDeferred

class FakeAuthRepository : AuthRepository {

    data class RegisterCall(
        val fullName: String,
        val email: String,
        val password: String
    )

    data class LoginCall(
        val email: String,
        val password: String
    )

    var registerResult: AuthResult = AuthResult.Success
    var loginResult: AuthResult = AuthResult.Success

    var registerGate: CompletableDeferred<Unit>? = null
    var loginGate: CompletableDeferred<Unit>? = null

    var registerCalls = mutableListOf<RegisterCall>()
    var loginCalls = mutableListOf<LoginCall>()

    override suspend fun register(
        fullName: String,
        email: String,
        password: String
    ): AuthResult {
        registerCalls += RegisterCall(fullName, email, password)
        registerGate?.await()
        return registerResult
    }

    override suspend fun login(
        email: String,
        password: String
    ): AuthResult {
        loginCalls += LoginCall(email, password)
        loginGate?.await()
        return loginResult
    }
}