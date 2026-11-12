package com.cornucopiacruise.ccl.feature.app.presentation

import com.cornucopiacruise.ccl.feature.app.domain.User
import com.cornucopiacruise.ccl.feature.app.domain.repository.FakeAppRepository
import com.cornucopiacruise.ccl.feature.app.domain.usecase.ObserveUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class AppViewModeTest {

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createViewModel(repository: FakeAppRepository = FakeAppRepository()): AppViewModel =
        AppViewModel(ObserveUserUseCase(repository))

    @Test
    fun `session is loading until repository emits`() {
        val viewModel = createViewModel()

        assertEquals(AppSessionState.Loading, viewModel.sessionState.value)
    }

    @Test
    fun `signed in user gives SignedIn`() {
        val repository = FakeAppRepository()
        val viewModel = createViewModel(repository)

        repository.emitUser(User("uid-1"))

        assertEquals(AppSessionState.SignedIn, viewModel.sessionState.value)
    }

    @Test
    fun `no user gives SignedOut`() {
        val repository = FakeAppRepository()
        val viewModel = createViewModel(repository)

        repository.emitUser(null)

        assertEquals(AppSessionState.SignedOut, viewModel.sessionState.value)
    }

    @Test
    fun `session follows user changes`() {
        val repository = FakeAppRepository()
        val viewModel = createViewModel(repository)

        repository.emitUser(User("uid-1"))
        assertEquals(AppSessionState.SignedIn, viewModel.sessionState.value)

        repository.emitUser(null)
        assertEquals(AppSessionState.SignedOut, viewModel.sessionState.value)
    }
}