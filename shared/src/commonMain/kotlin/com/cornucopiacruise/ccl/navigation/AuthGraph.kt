package com.cornucopiacruise.ccl.navigation

import kotlinx.serialization.Serializable

@Serializable
data object AuthGraph

sealed class AuthRoute {

    @Serializable
    data object Login : AuthRoute()

    @Serializable
    data object Register : AuthRoute()
}