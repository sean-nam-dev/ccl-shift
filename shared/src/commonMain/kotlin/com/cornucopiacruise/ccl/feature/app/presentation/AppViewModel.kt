package com.cornucopiacruise.ccl.feature.app.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cornucopiacruise.ccl.feature.app.domain.usecase.ObserveUserUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class AppViewModel(
    observeUserUseCase: ObserveUserUseCase
) : ViewModel() {
    val sessionState: StateFlow<AppSessionState> = observeUserUseCase()
        .map { user -> if (user != null) AppSessionState.SignedIn else AppSessionState.SignedOut }
        .stateIn(viewModelScope, SharingStarted.Eagerly, AppSessionState.Loading)
}