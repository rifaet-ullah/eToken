package com.apollovisa.etoken.ui.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Key
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apollovisa.etoken.ui.components.InputField
import com.apollovisa.etoken.ui.theme.ETokenTheme

@Composable
fun LogInScreenContent(
    viewState: LogInViewState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLogInClick: () -> Unit
) {
    var showPassword by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            "Log In",
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(24.dp))
        InputField(
            value = viewState.email,
            onChange = onEmailChange,
            placeholder = "Email",
            leadingIcon = Icons.Outlined.Email,
            autoCorrectEnable = false,
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next,
            enable = viewState is LogInViewState.InputState,
            errorMessage = viewState.emailError,
        )
        Spacer(modifier = Modifier.height(8.dp))
        InputField(
            value = viewState.password,
            onChange = onPasswordChange,
            placeholder = "Password",
            leadingIcon = Icons.Outlined.Key,
            trailingIcon = if (showPassword) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
            onTrailingIconClick = {
                showPassword = !showPassword
            },
            autoCorrectEnable = false,
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Go,
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            enable = viewState is LogInViewState.InputState,
            errorMessage = viewState.passwordError,
        )

        viewState.errorMessage?.let {
            Text(
                it,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
            )
        }

        Button(
            onClick = onLogInClick,
            enabled = viewState.emailError == null && viewState.passwordError == null,
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login")
                if (viewState is LogInViewState.AuthenticationState) {
                    Spacer(modifier = Modifier.width(4.dp))
                    CircularProgressIndicator(
                        strokeWidth = 2.dp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LogInScreenContentInputStatePreview() {
    ETokenTheme {
        LogInScreenContent(
            viewState = LogInViewState.InputState(),
            onEmailChange = {},
            onPasswordChange = {},
            onLogInClick = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun LogInScreenContentAuthenticationStatePreview() {
    ETokenTheme {
        LogInScreenContent(
            viewState = LogInViewState.AuthenticationState,
            onEmailChange = {},
            onPasswordChange = {},
            onLogInClick = {})
    }
}
