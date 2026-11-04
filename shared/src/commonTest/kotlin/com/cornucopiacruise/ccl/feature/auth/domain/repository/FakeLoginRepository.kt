package com.cornucopiacruise.ccl.feature.auth.domain.repository

import com.cornucopiacruise.ccl.feature.auth.domain.repository.login.LoginRepository
import com.cornucopiacruise.ccl.feature.auth.domain.repository.login.LoginResult
import kotlinx.coroutines.CompletableDeferred

class FakeLoginRepository : LoginRepository {

    data class LoginCall(
        val email: String,
        val password: String
    )

    var loginResult: LoginResult = LoginResult.Success
    var loginGate: CompletableDeferred<Unit>? = null
    var loginCalls = mutableListOf<LoginCall>()

    override suspend fun login(
        email: String,
        password: String
    ): LoginResult {
        loginCalls += LoginCall(email, password)
        loginGate?.await()
        return loginResult
    }
}