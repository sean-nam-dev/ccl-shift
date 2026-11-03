package com.cornucopiacruise.ccl.feature.auth.presentation.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cclshift.shared.generated.resources.Res
import cclshift.shared.generated.resources.log_in
import cclshift.shared.generated.resources.or
import cclshift.shared.generated.resources.register
import com.cornucopiacruise.ccl.core.presentation.theme.CCLShiftTheme
import com.cornucopiacruise.ccl.core.presentation.theme.dimens
import com.cornucopiacruise.ccl.feature.auth.presentation.component.AppFilledButton
import com.cornucopiacruise.ccl.feature.auth.presentation.component.AppOutlinedButton
import com.cornucopiacruise.ccl.feature.auth.presentation.component.EmailOutlinedTextField
import com.cornucopiacruise.ccl.feature.auth.presentation.component.PasswordOutlinedTextField
import com.cornucopiacruise.ccl.feature.auth.presentation.component.TextedDivider
import org.jetbrains.compose.resources.stringResource

@Composable
fun LoginUI(
    state: LoginState,
    snackBarHostState: SnackbarHostState,
    onEvent: (LoginEvent) -> Unit
) {
    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .imePadding()
                .padding(MaterialTheme.dimens.screenPadding),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.spaceXL)
        ) {
            EmailOutlinedTextField(
                value = state.email,
                onValueChange = { onEvent(LoginEvent.EmailChange(it)) },
                onClear = { onEvent(LoginEvent.ClearEmail) },
                isError = state.isEmailError
            )

            PasswordOutlinedTextField(
                value = state.password,
                onValueChange = { onEvent(LoginEvent.PasswordChange(it)) },
                isPasswordVisible = state.isPasswordVisible,
                togglePasswordVisibility = { onEvent(LoginEvent.TogglePasswordVisibility) },
                isError = state.isPasswordError
            )

            if (state.isLoading) {
                CircularProgressIndicator(trackColor = MaterialTheme.colorScheme.onPrimary)
            } else {
                AppFilledButton(
                    modifier = Modifier.fillMaxWidth(),
                    contentText = stringResource(Res.string.log_in),
                    onClick = { onEvent(LoginEvent.LogInClick) }
                )
            }

            TextedDivider(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(Res.string.or)
            )

            AppOutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                contentText = stringResource(Res.string.register),
                onClick = { onEvent(LoginEvent.RegisterClick) }
            )
        }
    }
}

@Preview
@Composable
private fun LoginUIPreview() {
    CCLShiftTheme {
        LoginUI(
            state = LoginState(),
            snackBarHostState = SnackbarHostState(),
            onEvent = {}
        )
    }
}