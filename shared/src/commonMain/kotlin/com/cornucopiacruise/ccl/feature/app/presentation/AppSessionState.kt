package com.cornucopiacruise.ccl.feature.app.presentation

sealed interface AppSessionState {
    data object Loading : AppSessionState
    data object SignedIn : AppSessionState
    data object SignedOut : AppSessionState
}