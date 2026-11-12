package com.cornucopiacruise.ccl.feature.app.domain.repository

import com.cornucopiacruise.ccl.feature.app.domain.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class FakeAppRepository : AppRepository {

    private val userFlow = MutableSharedFlow<User?>(replay = 1)

    override fun observeUser(): Flow<User?> = userFlow

    fun emitUser(user: User?) {
        userFlow.tryEmit(user)
    }
}