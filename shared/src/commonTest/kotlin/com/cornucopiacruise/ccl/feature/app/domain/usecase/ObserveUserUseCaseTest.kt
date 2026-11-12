package com.cornucopiacruise.ccl.feature.app.domain.usecase

import app.cash.turbine.test
import com.cornucopiacruise.ccl.feature.app.domain.User
import com.cornucopiacruise.ccl.feature.app.domain.repository.FakeAppRepository
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ObserveUserUseCaseTest {

    @Test
    fun `emits users from repository`() = runTest {
        val repository = FakeAppRepository()
        val useCase = ObserveUserUseCase(repository)

        useCase().test {
            repository.emitUser(User("uid-1"))
            assertEquals(User("uid-1"), awaitItem())

            repository.emitUser(null)
            assertNull(awaitItem())
        }
    }
}