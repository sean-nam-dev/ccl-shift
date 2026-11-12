package com.cornucopiacruise.ccl.feature.app.domain.repository

import com.cornucopiacruise.ccl.feature.app.domain.User
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    fun observeUser(): Flow<User?>
}