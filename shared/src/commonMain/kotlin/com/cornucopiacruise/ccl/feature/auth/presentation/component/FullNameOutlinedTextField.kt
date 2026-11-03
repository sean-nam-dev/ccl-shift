package com.cornucopiacruise.ccl.feature.auth.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import cclshift.shared.generated.resources.Res
import cclshift.shared.generated.resources.full_name
import cclshift.shared.generated.resources.ic_outlined_cancel_24
import cclshift.shared.generated.resources.ic_outlined_perm_identity_24
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun FullNameOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    onClear: () -> Unit,
    isError: Boolean
) {
    AppOutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        labelText = stringResource(Res.string.full_name),
        leadingIconPainter = painterResource(Res.drawable.ic_outlined_perm_identity_24),
        trailingIcon = if (value.isEmpty()) {
            null
        } else {
            TrailingIcon(
                painter = painterResource(Res.drawable.ic_outlined_cancel_24),
                onClick = onClear
            )
        },
        isError = isError,
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Words,
            imeAction = ImeAction.Next
        )
    )
}