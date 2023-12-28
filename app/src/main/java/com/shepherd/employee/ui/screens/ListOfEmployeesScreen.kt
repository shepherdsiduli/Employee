package com.shepherd.employee.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.shepherd.employee.R
import com.shepherd.employee.networking.data.response.Employee
import com.shepherd.employee.ui.navigation.Screen
import com.shepherd.employee.ui.screens.composables.LoadImageFromUrl
import com.shepherd.employee.ui.screens.composables.ProgressView
import com.shepherd.employee.ui.screens.composables.SimpleAppBar
import com.shepherd.employee.viewModel.EmployeeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListOfEmployeesScreen(
    navController: NavHostController,
    viewModel: EmployeeViewModel,
) {
    val scaffoldState = rememberScrollState()

    Scaffold(
        topBar = {
            SimpleAppBar(title = stringResource(id = R.string.list_of_employees))
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(state = scaffoldState),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val uiState by viewModel.employeeListUiState.collectAsState()

            when {
                uiState.isLoading -> {
                    ProgressView()
                }

                uiState.isError -> {
                    Text(
                        text = stringResource(id = R.string.error_occurred),
                        color = Color.Red,
                        modifier = Modifier.padding(bottom = 10.dp),
                        fontSize = 16.sp,
                    )
                }

                uiState.isSuccess -> {
                    EmployeeList(
                        navController = navController,
                        employees = uiState.employees,
                        modifier = Modifier.padding(4.dp),
                        viewModel = viewModel,
                    )
                }
            }

            LaunchedEffect(Unit) {
                viewModel.fetchEmployees()
            }
        }
    }
}

@Composable
fun EmployeeList(navController: NavHostController, employees: List<Employee>, modifier: Modifier, viewModel: EmployeeViewModel) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.SpaceEvenly,

    ) {
        employees.forEach { employee ->
            Row(
                modifier = modifier.clickable {
                    viewModel.setEmployee(employee = employee)
                    navController.navigate(Screen.SelectEmployeeScreen.route)
                },
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                LoadImageFromUrl(employee.avatar)

                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.SpaceEvenly,

                ) {
                    Text(text = "${employee.firstName} ${employee.lastName}")
                    Text(text = employee.email)
                }
            }
            Divider(
                modifier = Modifier
                    .padding(vertical = 1.dp),
                color = Color.Black,
                thickness = 1.dp,
            )
        }
    }
}
