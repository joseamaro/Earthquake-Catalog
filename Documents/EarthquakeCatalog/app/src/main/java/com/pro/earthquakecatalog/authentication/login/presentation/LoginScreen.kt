package com.pro.earthquakecatalog.authentication.login.presentation

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pro.earthquakecatalog.authentication.login.LoginEvent
import com.pro.earthquakecatalog.authentication.login.LoginState
import com.pro.earthquakecatalog.core.presentation.AuthenticationButton
import com.pro.earthquakecatalog.core.presentation.PasswordTextField
import com.pro.earthquakecatalog.core.presentation.TextField

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onNavigateRegister: () -> Unit,
    onNavigateHome: () -> Unit
) {

    val state = viewModel.uiState.collectAsState().value

    LaunchedEffect(key1 = state.loggedIn) {
        if (state.loggedIn) {
            onNavigateHome()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Header(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .background(Color.White)
        )
        Body(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(),
            loginState = state,
            onEvent = {
                viewModel.loginEvent(it)
            }
        )
        Footer(
            Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            onNavigateRegister = onNavigateRegister,
            loginState = state,
            onEvent = { viewModel.loginEvent(it) }
        )

        if (state.loading) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun Header(modifier: Modifier = Modifier) {
    val activity = LocalContext.current as Activity
    Row(modifier = modifier) {
        IconButton(onClick = {
            activity.finish()
        }) {
            Icon(imageVector = Icons.Default.Close, contentDescription = "icon close")
        }
    }
}

@Composable
fun Body(
    modifier: Modifier = Modifier,
    loginState: LoginState,
    onEvent: (LoginEvent) -> Unit
) {
    val focusManager = LocalFocusManager.current
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "Earthquake Catalog",
            style = TextStyle(fontSize = 25.sp, fontWeight = FontWeight.Bold, color = Color.Blue)
        )
        TextField(
            value = loginState.email, onValueChange = {
                onEvent(LoginEvent.EventEmail(it))
            }, placeholder = "Email", contentDescription = "Enter email",
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = Icons.Outlined.Email, keyboardOptions = KeyboardOptions(
                autoCorrect = false,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onAny = {
                focusManager.moveFocus(FocusDirection.Next)
            }), errorMessage = loginState.emailError,
            isEnabled = true
        )
        PasswordTextField(
            value = loginState.password,
            onValueChange = {
                onEvent(LoginEvent.EventPassword(it))
            },
            contentDescription = "Enter password",
            modifier = Modifier
                .fillMaxWidth(),
            errorMessage = loginState.passwordError,
            isEnabled = true,
            keyboardOptions = KeyboardOptions(
                autoCorrect = false,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onAny = {
                focusManager.clearFocus()
                onEvent(LoginEvent.EventLogin)
            })
        )
    }
}

@Composable
fun Footer(
    modifier: Modifier,
    loginState: LoginState,
    onNavigateRegister: () -> Unit,
    onEvent: (LoginEvent) -> Unit
) {
    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(20.dp)) {
        AuthenticationButton(
            modifier = Modifier.fillMaxWidth(),
            isEnabled = !loginState.loading,
            text = "Login"
        ) {
            onEvent(LoginEvent.EventLogin)
        }
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clickable {
                    onNavigateRegister()
                },
            text = "Don’t have an account? Sign up",
            style = TextStyle(fontSize = 12.sp, color = Color.Red)
        )
    }
}