package com.cornucopiacruise.ccl.feature.main.navigation

import kotlinx.serialization.Serializable

@Serializable
data object MainGraph

sealed class MainRoute {

    @Serializable
    data object Home : MainRoute()
}