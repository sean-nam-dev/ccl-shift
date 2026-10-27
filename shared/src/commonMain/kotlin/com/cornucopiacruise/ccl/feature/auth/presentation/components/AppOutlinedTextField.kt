package com.cornucopiacruise.ccl.feature.auth.presentation.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import cclshift.shared.generated.resources.Res
import cclshift.shared.generated.resources.ic_outlined_email_24
import cclshift.shared.generated.resources.ic_outlined_visibility_24
import com.cornucopiacruise.ccl.core.presentation.theme.PreviewScaffold
import org.jetbrains.compose.resources.painterResource
import kotlin.let

data class TrailingIcon(
    val painter: Painter,
    val onClick: () -> Unit
)

@Composable
fun AppOutlinedTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    labelText: String? = null,
    leadingIconPainter: Painter? = null,
    trailingIcon: TrailingIcon? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = labelText?.let { text ->
            {
                Text(
                    text = text
                )
            }
        },
        leadingIcon = leadingIconPainter?.let { painter ->
            {
                Icon(
                    painter = painter,
                    contentDescription = null
                )
            }
        },
        trailingIcon = trailingIcon?.let { icon ->
            {
                IconButton(
                    onClick = icon.onClick
                ) {
                    Icon(
                        painter = icon.painter,
                        contentDescription = null
                    )
                }
            }
        },
        singleLine = true,
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions
    )
}

@Preview
@Composable
private fun AppOutlinedTextFieldPreview() {
    PreviewScaffold {
        AppOutlinedTextField(
            labelText = "Password",
            leadingIconPainter = painterResource(Res.drawable.ic_outlined_email_24),
            trailingIcon = TrailingIcon(
                painter = painterResource(Res.drawable.ic_outlined_visibility_24),
                onClick = {}
            ),
            value = "",
            onValueChange = {}
        )
    }
}