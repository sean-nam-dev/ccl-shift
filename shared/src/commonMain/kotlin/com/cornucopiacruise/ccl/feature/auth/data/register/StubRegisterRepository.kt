package com.cornucopiacruise.ccl.feature.auth.data.register

import com.cornucopiacruise.ccl.feature.auth.domain.repository.register.RegisterError.EMAIL_ALREADY_IN_USE
import com.cornucopiacruise.ccl.feature.auth.domain.repository.register.RegisterError.NETWORK
import com.cornucopiacruise.ccl.feature.auth.domain.repository.register.RegisterError.UNKNOWN
import com.cornucopiacruise.ccl.feature.auth.domain.repository.register.RegisterRepository
import com.cornucopiacruise.ccl.feature.auth.domain.repository.register.RegisterResult
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

class StubRegisterRepository(
    val duration: Long = 1_000L
) : RegisterRepository {
    override suspend fun register(
        fullName: String,
        email: String,
        password: String
    ): RegisterResult {
        delay(duration.milliseconds)
        return when (email) {
            "already@test.com" -> RegisterResult.Failure(EMAIL_ALREADY_IN_USE)
            "network@test.com" -> RegisterResult.Failure(NETWORK)
            "unknown@test.com" -> RegisterResult.Failure(UNKNOWN)
            else -> RegisterResult.Success
        }
    }
}