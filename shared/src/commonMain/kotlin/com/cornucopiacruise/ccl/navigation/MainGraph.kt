package com.cornucopiacruise.ccl.navigation

import kotlinx.serialization.Serializable

@Serializable
data object MainGraph

sealed class MainRoute {

    @Serializable
    data object Home : MainRoute()
}