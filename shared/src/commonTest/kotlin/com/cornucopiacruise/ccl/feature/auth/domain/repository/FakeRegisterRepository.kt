package com.cornucopiacruise.ccl.feature.auth.domain.repository

import com.cornucopiacruise.ccl.feature.auth.domain.repository.register.RegisterRepository
import com.cornucopiacruise.ccl.feature.auth.domain.repository.register.RegisterResult
import kotlinx.coroutines.CompletableDeferred

class FakeRegisterRepository : RegisterRepository {

    data class Call(
        val fullName: String,
        val email: String,
        val password: String
    )

    var registerResult: RegisterResult = RegisterResult.Success
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