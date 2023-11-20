package com.shepherd.employee.ui.navigation

sealed class Screen(val route: String) {
    object LoginScreen : Screen("Login")
    object SelectEmployeeScreen : Screen("SelectEmployee")
    object ListOfEmployeesScreen : Screen("ListOfEmployees")
    object SelectColourScreen : Screen("SelectColour")
    object ReviewScreen : Screen("Review")
    object AdditionalInformationScreen : Screen("AdditionalInformation")
    object SuccessfulScreen : Screen("Successful")
}
