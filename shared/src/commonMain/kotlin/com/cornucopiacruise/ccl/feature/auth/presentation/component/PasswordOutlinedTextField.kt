package com.cornucopiacruise.ccl.feature.auth.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import cclshift.shared.generated.resources.Res
import cclshift.shared.generated.resources.ic_outlined_lock_24
import cclshift.shared.generated.resources.ic_outlined_visibility_24
import cclshift.shared.generated.resources.ic_outlined_visibility_off_24
import cclshift.shared.generated.resources.password
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun PasswordOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    isPasswordVisible: Boolean,
    togglePasswordVisibility: () -> Unit,
    isError: Boolean
) {
    AppOutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        labelText = stringResource(Res.string.password),
        leadingIconPainter = painterResource(Res.drawable.ic_outlined_lock_24),
        trailingIcon = TrailingIcon(
            painter = painterResource(
                when (isPasswordVisible) {
                    true -> Res.drawable.ic_outlined_visibility_24
                    false -> Res.drawable.ic_outlined_visibility_off_24
                }
            ),
            onClick = togglePasswordVisibility
        ),
        isError = isError,
        visualTransformation = when (isPasswordVisible) {
            true -> VisualTransformation.None
            false -> PasswordVisualTransformation()
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        )
    )
}