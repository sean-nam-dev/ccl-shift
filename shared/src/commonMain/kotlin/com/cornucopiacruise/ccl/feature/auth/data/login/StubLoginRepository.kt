package com.cornucopiacruise.ccl.feature.auth.data.login

import com.cornucopiacruise.ccl.feature.auth.domain.repository.login.LoginError
import com.cornucopiacruise.ccl.feature.auth.domain.repository.login.LoginRepository
import com.cornucopiacruise.ccl.feature.auth.domain.repository.login.LoginResult
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

class StubLoginRepository(
    val duration: Long = 1_000L
) : LoginRepository {
    override suspend fun login(
        email: String,
        password: String
    ): LoginResult {
        delay(duration.milliseconds)
        return when (email) {
            "invalid@test.com" -> LoginResult.Failure(LoginError.INVALID_CREDENTIALS)
            "network@test.com" -> LoginResult.Failure(LoginError.NETWORK)
            "unknown@test.com" -> LoginResult.Failure(LoginError.UNKNOWN)
            else -> LoginResult.Success
        }
    }
}