package com.cornucopiacruise.ccl.feature.auth.domain.usecase

import com.cornucopiacruise.ccl.feature.auth.domain.repository.AuthRepository
import com.cornucopiacruise.ccl.feature.auth.domain.repository.AuthResult

class RegisterUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        fullName: String,
        email: String,
        password: String
    ): AuthResult = authRepository.register(fullName, email, password)
}