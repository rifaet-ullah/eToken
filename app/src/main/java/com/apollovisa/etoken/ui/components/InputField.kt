package com.apollovisa.etoken.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Key
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apollovisa.etoken.ui.theme.ETokenTheme

@Composable
fun InputField(
    value: String,
    onChange: (String) -> Unit,
    placeholder: String,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    onTrailingIconClick: () -> Unit = {},
    autoCorrectEnable: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    enable: Boolean = true,
    errorMessage: String? = null
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = onChange,
            placeholder = { Text(placeholder) },
            label = { Text(placeholder) },
            leadingIcon = if (leadingIcon == null) {
                null
            } else {
                {
                    Icon(
                        imageVector = leadingIcon, contentDescription = placeholder
                    )
                }
            },
            trailingIcon = if (trailingIcon == null) {
                null
            } else {
                {
                    IconButton(onClick = onTrailingIconClick) {
                        Icon(
                            imageVector = trailingIcon, contentDescription = placeholder
                        )
                    }
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                autoCorrectEnabled = autoCorrectEnable,
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            visualTransformation = visualTransformation,
            enabled = enable,
            isError = errorMessage != null,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                disabledBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
            ),
            modifier = Modifier.fillMaxWidth()
        )
        errorMessage?.let {
            Text(
                it,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun InputFieldEnabledPreview() {
    ETokenTheme {
        InputField(
            value = "",
            onChange = {},
            placeholder = "Full name"
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun InputFieldEnabledWithLeadingIconPreview() {
    ETokenTheme {
        InputField(
            value = "",
            onChange = {},
            placeholder = "Password",
            leadingIcon = Icons.Outlined.Key
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun InputFieldEnabledWithLeadingAndTrailingIconPreview() {
    ETokenTheme {
        InputField(
            value = "",
            onChange = {},
            placeholder = "Password",
            leadingIcon = Icons.Outlined.Key,
            trailingIcon = Icons.Outlined.Visibility
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun InputFieldDisabledWithLeadingAndTrailingIconPreview() {
    ETokenTheme {
        InputField(
            value = "",
            onChange = {},
            placeholder = "Password",
            leadingIcon = Icons.Outlined.Key,
            trailingIcon = Icons.Outlined.Visibility,
            enable = false
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun InputFieldEnabledWithErrorMessagePreview() {
    ETokenTheme {
        InputField(
            value = "Rifat123",
            onChange = {},
            placeholder = "Full name",
            errorMessage = "Name cannot have numbers in it."
        )
    }
}
