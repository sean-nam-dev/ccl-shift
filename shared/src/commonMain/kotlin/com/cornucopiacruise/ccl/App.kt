package com.cornucopiacruise.ccl

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.cornucopiacruise.ccl.core.presentation.theme.CCLShiftTheme
import com.cornucopiacruise.ccl.di.appModule
import com.cornucopiacruise.ccl.navigation.AuthGraph
import com.cornucopiacruise.ccl.navigation.NavGraph
import org.koin.compose.KoinApplication
import org.koin.dsl.koinConfiguration

@Composable
@Preview
fun App() {
    KoinApplication(
        configuration = koinConfiguration { modules(appModule) }
    ) {
        CCLShiftTheme {
            NavGraph(AuthGraph)
        }
    }
}