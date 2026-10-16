package com.cornucopiacruise.ccl.feature.auth.domain.repository

import kotlinx.coroutines.CompletableDeferred

class FakeAuthRepository : AuthRepository {

    data class Call(
        val fullName: String,
        val email: String,
        val password: String
    )

    var registerResult = RegisterResult.Success
    var registerCallList = mutableListOf<Call>()
    var registerGate: CompletableDeferred<Unit>? = null

    override suspend fun register(
        fullName: String,
        email: String,
        password: String
    ): RegisterResult {
        registerCallList += Call(fullName, email, password)
        registerGate?.await()
        return registerResult
    }
}