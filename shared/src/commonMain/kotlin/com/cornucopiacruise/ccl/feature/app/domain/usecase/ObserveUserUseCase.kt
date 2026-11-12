package com.cornucopiacruise.ccl.feature.app.domain.usecase

import com.cornucopiacruise.ccl.feature.app.domain.User
import com.cornucopiacruise.ccl.feature.app.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow

class ObserveUserUseCase(
    private val appRepository: AppRepository
) {
    operator fun invoke(): Flow<User?> = appRepository.observeUser()
}