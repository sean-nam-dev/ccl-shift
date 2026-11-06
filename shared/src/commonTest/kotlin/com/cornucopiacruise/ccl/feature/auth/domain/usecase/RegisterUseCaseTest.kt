package com.cornucopiacruise.ccl.feature.auth.domain.usecase

import com.cornucopiacruise.ccl.feature.auth.domain.repository.AuthError
import com.cornucopiacruise.ccl.feature.auth.domain.repository.AuthResult
import com.cornucopiacruise.ccl.feature.auth.domain.repository.FakeAuthRepository
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

private const val NEW_NAME = "John Doe"
private const val NEW_EMAIL = "test@example.com"
private const val NEW_PASSWORD = "password123!!!"

class RegisterUseCaseTest {

    @Test
    fun `passes arguments to repository`() = runTest {
        val repository = FakeAuthRepository()
        val useCase = RegisterUseCase(repository)

        useCase(
            fullName = NEW_NAME,
            email = NEW_EMAIL,
            password = NEW_PASSWORD
        )

        assertEquals(
            expected = listOf(
                FakeAuthRepository.RegisterCall(
                    fullName = NEW_NAME,
                    email = NEW_EMAIL,
                    password = NEW_PASSWORD
                )
            ),
            actual = repository.registerCalls
        )
    }

    @Test
    fun `returns success from repository`() = runTest {
        val useCase = RegisterUseCase(FakeAuthRepository())

        assertEquals(
            AuthResult.Success,
            useCase(
                fullName = NEW_NAME,
                email = NEW_EMAIL,
                password = NEW_PASSWORD
            )
        )
    }

    @Test
    fun `returns every failure from repository unchanged`() = runTest {
        AuthError.entries.forEach { error ->
            val repository = FakeAuthRepository().apply {
                registerResult = AuthResult.Failure(error)
            }

            val result = RegisterUseCase(repository)(
                fullName = NEW_NAME,
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