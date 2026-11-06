package com.cornucopiacruise.ccl.feature.auth.domain.usecase

import com.cornucopiacruise.ccl.feature.auth.domain.repository.AuthError
import com.cornucopiacruise.ccl.feature.auth.domain.repository.AuthResult
import com.cornucopiacruise.ccl.feature.auth.domain.repository.FakeAuthRepository
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

private const val NEW_EMAIL = "test@example.com"
private const val NEW_PASSWORD = "password123!!!"

class LoginUseCaseTest {

    @Test
    fun `passes arguments to repository`() = runTest {
        val repository = FakeAuthRepository()
        val useCase = LoginUseCase(repository)

        useCase(
            email = NEW_EMAIL,
            password = NEW_PASSWORD
        )

        assertEquals(
            listOf(
                FakeAuthRepository.LoginCall(
                    email = NEW_EMAIL,
                    password = NEW_PASSWORD
                )
            ),
            repository.loginCalls
        )
    }

    @Test
    fun `returns success from repository`() = runTest {
        val useCase = LoginUseCase(FakeAuthRepository())

        assertEquals(
            AuthResult.Success,
            useCase(
                email = NEW_EMAIL,
                password = NEW_PASSWORD
            )
        )
    }

    @Test
    fun `returns every failure from repository unchanged`() = runTest {
        AuthError.entries.forEach { error ->
            val repository = FakeAuthRepository().apply {
                loginResult = AuthResult.Failure(error)
            }

            val result = LoginUseCase(repository)(
                email = NEW_EMAIL,
                password = NEW_PASSWORD
            )

            assertEquals(
                AuthResult.Failure(error),
                result
            )
        }
    }
}