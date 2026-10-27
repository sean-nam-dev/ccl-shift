package com.cornucopiacruise.ccl

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import org.jetbrains.compose.resources.painterResource

import cclshift.shared.generated.resources.Res
import cclshift.shared.generated.resources.compose_multiplatform
import com.cornucopiacruise.ccl.core.presentation.theme.CCLShiftTheme
import com.cornucopiacruise.ccl.feature.auth.domain.repository.StubAuthRepository
import com.cornucopiacruise.ccl.feature.auth.presentation.register.RegisterScreen
import com.cornucopiacruise.ccl.feature.auth.presentation.register.RegisterViewModel

@Composable
@Preview
fun App() {
    CCLShiftTheme {
        RegisterScreen(
            viewModel = viewModel { RegisterViewModel(StubAuthRepository()) },
            onNavigateToLogin = {},
            onNavigateToHome = {}
        )
    }
}