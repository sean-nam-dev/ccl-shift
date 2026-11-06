package com.cornucopiacruise.ccl

import androidx.compose.runtime.Composable
import com.cornucopiacruise.ccl.core.log.AppConfig
import com.cornucopiacruise.ccl.core.presentation.theme.CCLShiftTheme
import com.cornucopiacruise.ccl.di.appModule
import com.cornucopiacruise.ccl.di.loggingModule
import com.cornucopiacruise.ccl.navigation.AuthGraph
import com.cornucopiacruise.ccl.navigation.NavGraph
import org.koin.compose.KoinApplication
import org.koin.dsl.koinConfiguration
import org.koin.dsl.module

@Composable
fun App(isDebug: Boolean) {
    KoinApplication(
        configuration = koinConfiguration {
            modules(
                module { single { AppConfig(isDebug) } },
                loggingModule,
                appModule
            )
        }
    ) {
        CCLShiftTheme {
            NavGraph(AuthGraph)
        }
    }
}