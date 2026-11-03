package com.cornucopiacruise.ccl.feature.auth.presentation.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import cclshift.shared.generated.resources.Res
import cclshift.shared.generated.resources.full_name
import cclshift.shared.generated.resources.ic_outlined_cancel_24
import cclshift.shared.generated.resources.ic_outlined_perm_identity_24
import cclshift.shared.generated.resources.log_in
import cclshift.shared.generated.resources.or
import cclshift.shared.generated.resources.register
import com.cornucopiacruise.ccl.core.presentation.theme.CCLShiftTheme
import com.cornucopiacruise.ccl.core.presentation.theme.dimens
import com.cornucopiacruise.ccl.feature.auth.presentation.component.AppFilledButton
import com.cornucopiacruise.ccl.feature.auth.presentation.component.AppOutlinedButton
import com.cornucopiacruise.ccl.feature.auth.presentation.component.AppOutlinedTextField
import com.cornucopiacruise.ccl.feature.auth.presentation.component.EmailOutlinedTextField
import com.cornucopiacruise.ccl.feature.auth.presentation.component.FullNameOutlinedTextField
import com.cornucopiacruise.ccl.feature.auth.presentation.component.PasswordOutlinedTextField
import com.cornucopiacruise.ccl.feature.auth.presentation.component.TextedDivider
import com.cornucopiacruise.ccl.feature.auth.presentation.component.TrailingIcon
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun RegisterUI(
    state: RegisterState,
    snackBarHostState: SnackbarHostState,
    onEvent: (RegisterEvent) -> Unit
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
            FullNameOutlinedTextField(
                value = state.fullName,
                onValueChange = { onEvent(RegisterEvent.FullNameChange(it)) },
                onClear = { onEvent(RegisterEvent.ClearFullName) },
                isError = state.fullNameError != null
            )

            EmailOutlinedTextField(
                value = state.email,
                onValueChange = { onEvent(RegisterEvent.EmailChange(it)) },
                onClear = { onEvent(RegisterEvent.ClearEmail) },
                isError = state.emailError != null
            )

            PasswordOutlinedTextField(
                value = state.password,
                onValueChange = { onEvent(RegisterEvent.PasswordChange(it)) },
                isPasswordVisible = state.isPasswordVisible,
                togglePasswordVisibility = { onEvent(RegisterEvent.TogglePasswordVisibility) },
                isError = state.passwordError != null
            )

            if (state.isLoading) {
                CircularProgressIndicator(trackColor = MaterialTheme.colorScheme.onPrimary)
            } else {
                AppFilledButton(
                    modifier = Modifier.fillMaxWidth(),
                    contentText = stringResource(Res.string.register),
                    onClick = { onEvent(RegisterEvent.RegisterClick) }
                )
            }

            TextedDivider(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(Res.string.or)
            )

            AppOutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                contentText = stringResource(Res.string.log_in),
                onClick = { onEvent(RegisterEvent.LogInClick) }
            )
        }
    }
}

@Preview
@Composable
private fun RegisterUIPreview() {
    CCLShiftTheme {
        RegisterUI(
            state = RegisterState(),
            snackBarHostState = SnackbarHostState(),
            onEvent = {}
        )
    }
}