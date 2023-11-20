package com.shepherd.employee.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.shepherd.employee.R
import com.shepherd.employee.ui.navigation.Screen
import com.shepherd.employee.ui.screens.composables.SimpleAppBar
import com.shepherd.employee.viewModel.EmployeeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuccessfulScreen(
    navController: NavHostController,
    viewModel: EmployeeViewModel,
) {
    Scaffold(
        topBar = {
            SimpleAppBar(title = stringResource(id = R.string.app_name))
        },
    ) { _ ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Button(
                onClick = {
                    viewModel.clearData()
                    navController.navigate(Screen.SelectEmployeeScreen.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            ) {
                Text(text = stringResource(id = R.string.done))
            }
        }
    }
}
