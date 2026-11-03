package com.cornucopiacruise.ccl.feature.auth.domain.repository.login

interface LoginRepository {

    suspend fun login(email: String, password: String): LoginResult
}