package com.cornucopiacruise.ccl.feature.auth.domain.usecase

import com.cornucopiacruise.ccl.feature.auth.domain.repository.AuthRepository
import com.cornucopiacruise.ccl.feature.auth.domain.repository.AuthResult

class LoginUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String
    ): AuthResult = authRepository.login(email, password)
}