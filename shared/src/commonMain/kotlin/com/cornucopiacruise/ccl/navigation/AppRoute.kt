package com.cornucopiacruise.ccl.navigation

import kotlinx.serialization.Serializable

sealed class AppRoute {

    @Serializable
    data object Loader : AppRoute()
}