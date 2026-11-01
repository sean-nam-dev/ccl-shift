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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import cclshift.shared.generated.resources.Res
import cclshift.shared.generated.resources.email
import cclshift.shared.generated.resources.full_name
import cclshift.shared.generated.resources.ic_outlined_cancel_24
import cclshift.shared.generated.resources.ic_outlined_email_24
import cclshift.shared.generated.resources.ic_outlined_lock_24
import cclshift.shared.generated.resources.ic_outlined_perm_identity_24
import cclshift.shared.generated.resources.ic_outlined_visibility_24
import cclshift.shared.generated.resources.ic_outlined_visibility_off_24
import cclshift.shared.generated.resources.log_in
import cclshift.shared.generated.resources.or
import cclshift.shared.generated.resources.password
import cclshift.shared.generated.resources.register
import com.cornucopiacruise.ccl.core.presentation.theme.CCLShiftTheme
import com.cornucopiacruise.ccl.feature.util.PreviewScaffold
import com.cornucopiacruise.ccl.core.presentation.theme.dimens
import com.cornucopiacruise.ccl.feature.auth.presentation.component.AppFilledButton
import com.cornucopiacruise.ccl.feature.auth.presentation.component.AppOutlinedButton
import com.cornucopiacruise.ccl.feature.auth.presentation.component.AppOutlinedTextField
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
            AppOutlinedTextField(
                value = state.fullName,
                onValueChange = { onEvent(RegisterEvent.FullNameChange(it)) },
                modifier = Modifier.fillMaxWidth(),
                labelText = stringResource(Res.string.full_name),
                leadingIconPainter = painterResource(Res.drawable.ic_outlined_perm_identity_24),
                trailingIcon = if (state.fullName.isEmpty()) {
                    null
                } else {
                    TrailingIcon(
                        painter = painterResource(Res.drawable.ic_outlined_cancel_24),
                        onClick = { onEvent(RegisterEvent.ClearFullName) }
                    )
                },
                isError = state.fullNameError != null,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Next
                ),
            )

            AppOutlinedTextField(
                value = state.email,
                onValueChange = { onEvent(RegisterEvent.EmailChange(it)) },
                modifier = Modifier.fillMaxWidth(),
                labelText = stringResource(Res.string.email),
                leadingIconPainter = painterResource(Res.drawable.ic_outlined_email_24),
                trailingIcon = if (state.email.isEmpty()) {
                    null
                } else {
                    TrailingIcon(
                        painter = painterResource(Res.drawable.ic_outlined_cancel_24),
                        onClick = { onEvent(RegisterEvent.ClearEmail) }
                    )
                },
                isError = state.emailError != null,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Email
                )
            )

            AppOutlinedTextField(
                value = state.password,
                onValueChange = { onEvent(RegisterEvent.PasswordChange(it)) },
                modifier = Modifier.fillMaxWidth(),
                labelText = stringResource(Res.string.password),
                leadingIconPainter = painterResource(Res.drawable.ic_outlined_lock_24),
                trailingIcon = TrailingIcon(
                    painter = painterResource(
                        if (state.isPasswordVisible) Res.drawable.ic_outlined_visibility_24
                        else Res.drawable.ic_outlined_visibility_off_24
                    ),
                    onClick = { onEvent(RegisterEvent.TogglePasswordVisibility) }
                ),
                isError = state.passwordError != null,
                visualTransformation = if (state.isPasswordVisible) VisualTransformation.None
                else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                )
            )

            AppFilledButton(
                modifier = Modifier.fillMaxWidth(),
                contentText = stringResource(Res.string.register),
                onClick = { onEvent(RegisterEvent.RegisterClick) },
                content = if (state.isLoading) {
                    { CircularProgressIndicator(trackColor = MaterialTheme.colorScheme.onPrimary) }
                } else {
                    null
                }
            )

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

@Composable
@Preview
private fun RegisterUIPreview() {
    CCLShiftTheme {
        RegisterUI(
            state = RegisterState(),
            snackBarHostState = SnackbarHostState(),
            onEvent = {}
        )
    }
}