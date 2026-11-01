package com.cornucopiacruise.ccl.feature.auth.domain.repository.register

interface RegisterRepository {
    suspend fun register(fullName: String, email: String, password: String): RegisterResult
}