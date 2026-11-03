package com.cornucopiacruise.ccl.feature.auth.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import cclshift.shared.generated.resources.Res
import cclshift.shared.generated.resources.email
import cclshift.shared.generated.resources.ic_outlined_cancel_24
import cclshift.shared.generated.resources.ic_outlined_email_24
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun EmailOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    onClear: () -> Unit,
    isError: Boolean
) {
    AppOutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        labelText = stringResource(Res.string.email),
        leadingIconPainter = painterResource(Res.drawable.ic_outlined_email_24),
        trailingIcon = when (value.isEmpty()) {
            true -> null
            false -> TrailingIcon(
                painter = painterResource(Res.drawable.ic_outlined_cancel_24),
                onClick = onClear
            )
        },
        isError = isError,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Email
        )
    )
}