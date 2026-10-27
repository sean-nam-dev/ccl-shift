package com.cornucopiacruise.ccl.feature.auth.domain.repository

import androidx.compose.runtime.Composable
import cclshift.shared.generated.resources.Res
import cclshift.shared.generated.resources.email_already_in_use
import cclshift.shared.generated.resources.network_error
import cclshift.shared.generated.resources.unknown_error
import com.cornucopiacruise.ccl.feature.auth.domain.repository.RegisterError.*
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.stringResource
import kotlin.time.Duration.Companion.milliseconds

interface AuthRepository {
    suspend fun register(fullName: String, email: String, password: String): RegisterResult
}

sealed class RegisterResult {
    data object Success : RegisterResult()
    data class Failure(val reason: RegisterError) : RegisterResult()
}

enum class RegisterError {
    EMAIL_ALREADY_IN_USE,
    NETWORK,
    UNKNOWN
}

fun RegisterError.messageRes() = when (this) {
    EMAIL_ALREADY_IN_USE -> Res.string.email_already_in_use
    NETWORK -> Res.string.network_error
    UNKNOWN -> Res.string.unknown_error
}

class StubAuthRepository(
    val duration: Long = 1_000L
) : AuthRepository {
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