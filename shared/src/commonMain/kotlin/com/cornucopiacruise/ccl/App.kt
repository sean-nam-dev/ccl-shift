package com.cornucopiacruise.ccl

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.cornucopiacruise.ccl.core.presentation.theme.CCLShiftTheme
import com.cornucopiacruise.ccl.navigation.AuthGraph
import com.cornucopiacruise.ccl.navigation.NavGraph

@Composable
@Preview
fun App() {
    CCLShiftTheme {
        NavGraph(AuthGraph)
    }
}