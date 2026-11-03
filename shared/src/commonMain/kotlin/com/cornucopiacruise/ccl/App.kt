package com.cornucopiacruise.ccl

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.cornucopiacruise.ccl.core.presentation.theme.CCLShiftTheme
import com.cornucopiacruise.ccl.feature.auth.data.login.StubLoginRepository
import com.cornucopiacruise.ccl.feature.auth.data.register.StubRegisterRepository
import com.cornucopiacruise.ccl.navigation.AuthGraph
import com.cornucopiacruise.ccl.feature.auth.presentation.login.LoginViewModel
import com.cornucopiacruise.ccl.feature.auth.presentation.register.RegisterViewModel
import com.cornucopiacruise.ccl.navigation.NavGraph

val registerViewModel = RegisterViewModel(StubRegisterRepository())
val loginViewModel = LoginViewModel(StubLoginRepository())
@Composable
@Preview
fun App() {
    CCLShiftTheme {
        NavGraph(AuthGraph, registerViewModel, loginViewModel)
    }
}