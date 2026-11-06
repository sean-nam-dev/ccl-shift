package com.cornucopiacruise.ccl

import androidx.compose.ui.window.ComposeUIViewController
import kotlin.experimental.ExperimentalNativeApi

@OptIn(ExperimentalNativeApi::class)
@Suppress("unused", "FunctionName")
fun MainViewController() = ComposeUIViewController {
    App(isDebug = Platform.isDebugBinary)
}