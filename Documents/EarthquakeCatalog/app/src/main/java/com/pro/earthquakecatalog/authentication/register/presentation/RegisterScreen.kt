package com.pro.earthquakecatalog.authentication.register.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pro.earthquakecatalog.authentication.register.RegisterEvent
import com.pro.earthquakecatalog.core.presentation.AuthenticationButton
import com.pro.earthquakecatalog.core.presentation.PasswordTextField
import com.pro.earthquakecatalog.core.presentation.TextField

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    onNavigateHome: () -> Unit,
    onBack: () -> Unit
) {

    val state = viewModel.uiState.collectAsState().value

    LaunchedEffect(key1 = state.goToHome) {
        if (state.goToHome) {
            onNavigateHome()
        }
    }

    Scaffold(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
        .padding(16.dp), topBar = {
        Box(
            Modifier
                .fillMaxWidth()
        ) {
            IconButton(onClick = { onBack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Arrow Back"
                )
            }
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "Create Account",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Blue
            )
        }
    }, bottomBar = {
        AuthenticationButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Create Account",
            isEnabled = !state.isLoading
        ) {
            viewModel.onEvent(RegisterEvent.EventRegisterAccount)
        }
    }) {
        val focusManager = LocalFocusManager.current
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(it)
                .padding(top = 20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            TextField(
                value = state.name, onValueChange = { text ->
                    viewModel.onEvent(RegisterEvent.Name(text))
                }, placeholder = "Name", contentDescription = "Enter name",
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = Icons.Outlined.AccountBox, keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onAny = {
                    focusManager.moveFocus(FocusDirection.Next)
                }), errorMessage = state.nameError,
                isEnabled = true
            )

            TextField(
                value = state.lastName, onValueChange = { text ->
                    viewModel.onEvent(RegisterEvent.LastName(text))
                }, placeholder = "Last name", contentDescription = "Enter last name",
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = Icons.Outlined.AccountBox, keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onAny = {
                    focusManager.moveFocus(FocusDirection.Next)
                }), errorMessage = state.lastNameError,
                isEnabled = true
            )

            TextField(
                value = state.email, onValueChange = { text ->
                    viewModel.onEvent(RegisterEvent.Email(text))
                }, placeholder = "Email", contentDescription = "Enter email",
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = Icons.Outlined.Email, keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onAny = {
                    focusManager.moveFocus(FocusDirection.Next)
                }), errorMessage = state.emailError,
                isEnabled = true
            )
            PasswordTextField(
                value = state.password,
                onValueChange = { text ->
                    viewModel.onEvent(RegisterEvent.Password(text))
                },
                contentDescription = "Enter password",
                modifier = Modifier
                    .fillMaxWidth(),
                errorMessage = state.passwordError,
                isEnabled = true,
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onAny = {
                    focusManager.clearFocus()
                    viewModel.onEvent(RegisterEvent.EventLogin)
                })
            )
        }
        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }
}