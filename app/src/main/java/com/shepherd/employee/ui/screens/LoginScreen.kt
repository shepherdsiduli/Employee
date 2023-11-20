package com.shepherd.employee.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.shepherd.employee.R
import com.shepherd.employee.ui.navigation.Screen
import com.shepherd.employee.ui.screens.composables.ProgressView
import com.shepherd.employee.ui.screens.composables.SimpleAppBar
import com.shepherd.employee.viewModel.EmployeeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: EmployeeViewModel,
) {
    val uiState by viewModel.loginUiState.collectAsState()

    val emailState = remember { mutableStateOf(TextFieldValue()) }
    val passwordState = remember { mutableStateOf(TextFieldValue()) }

    Scaffold(
        topBar = {
            SimpleAppBar(title = stringResource(id = R.string.app_name))
        },
    ) { _ ->
        if (uiState.isLoading) {
            ProgressView()
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                OutlinedTextField(
                    value = emailState.value,
                    onValueChange = { emailState.value = it },
                    label = { Text(text = stringResource(id = R.string.username_email)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                )

                OutlinedTextField(
                    value = passwordState.value,
                    onValueChange = { passwordState.value = it },
                    label = { Text(text = stringResource(id = R.string.password)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                )

                if (uiState.isError) {
                    uiState.error?.let {
                        Text(
                            text = it,
                            color = Color.Red,
                            modifier = Modifier.padding(vertical = 8.dp),
                        )
                    }
                }

                Button(
                    onClick = {
                        if (emailState.value.text.isEmpty() || passwordState.value.text.isEmpty()) {
                            return@Button
                        }
                        viewModel.login(emailState.value.text, passwordState.value.text)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                ) {
                    Text(text = stringResource(id = R.string.login))
                }
            }
        }

        if (!uiState.isLoading && uiState.isSuccess) {
            navController.navigate(Screen.SelectEmployeeScreen.route)
        }
    }
}
