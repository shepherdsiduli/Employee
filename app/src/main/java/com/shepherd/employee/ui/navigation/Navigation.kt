package com.shepherd.employee.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shepherd.employee.ui.screens.AdditionalInformationScreen
import com.shepherd.employee.ui.screens.ColoursListScreen
import com.shepherd.employee.ui.screens.ListOfEmployeesScreen
import com.shepherd.employee.ui.screens.LoginScreen
import com.shepherd.employee.ui.screens.ReviewScreen
import com.shepherd.employee.ui.screens.SelectEmployeeScreenScreen
import com.shepherd.employee.ui.screens.SuccessfulScreen
import com.shepherd.employee.viewModel.EmployeeViewModel

@Composable
fun navigation(viewModel: EmployeeViewModel) {
    val navigationController = rememberNavController()
    NavHost(
        navController = navigationController,
        startDestination = Screen.LoginScreen.route,
    ) {
        composable(route = Screen.LoginScreen.route) {
            LoginScreen(navController = navigationController, viewModel = viewModel)
        }

        composable(route = Screen.SelectEmployeeScreen.route) {
            SelectEmployeeScreenScreen(navController = navigationController, viewModel = viewModel)
        }

        composable(route = Screen.ListOfEmployeesScreen.route) {
            ListOfEmployeesScreen(navController = navigationController, viewModel = viewModel)
        }

        composable(route = Screen.AdditionalInformationScreen.route) {
            AdditionalInformationScreen(navController = navigationController, viewModel = viewModel)
        }

        composable(route = Screen.SelectColourScreen.route) {
            ColoursListScreen(navController = navigationController, viewModel = viewModel)
        }

        composable(route = Screen.ReviewScreen.route) {
            ReviewScreen(navController = navigationController, viewModel = viewModel)
        }
        composable(route = Screen.SuccessfulScreen.route) {
            SuccessfulScreen(navController = navigationController, viewModel = viewModel)
        }
    }
}
