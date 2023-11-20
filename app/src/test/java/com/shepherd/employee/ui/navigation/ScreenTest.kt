package com.shepherd.employee.ui.navigation

import org.junit.Assert.assertEquals
import org.junit.Test

class ScreenTest {

    @Test
    fun testLoginScreenRoute() {
        val expectedRoute = "Login"
        val actualRoute = Screen.LoginScreen.route
        assertEquals(expectedRoute, actualRoute)
    }

    @Test
    fun testSelectEmployeeScreenRoute() {
        val expectedRoute = "SelectEmployee"
        val actualRoute = Screen.SelectEmployeeScreen.route
        assertEquals(expectedRoute, actualRoute)
    }

    @Test
    fun testListOfEmployeesScreenRoute() {
        val expectedRoute = "ListOfEmployees"
        val actualRoute = Screen.ListOfEmployeesScreen.route
        assertEquals(expectedRoute, actualRoute)
    }

    @Test
    fun testSelectColourScreenRoute() {
        val expectedRoute = "SelectColour"
        val actualRoute = Screen.SelectColourScreen.route
        assertEquals(expectedRoute, actualRoute)
    }

    @Test
    fun testReviewScreenRoute() {
        val expectedRoute = "Review"
        val actualRoute = Screen.ReviewScreen.route
        assertEquals(expectedRoute, actualRoute)
    }

    @Test
    fun testAdditionalInformationScreenRoute() {
        val expectedRoute = "AdditionalInformation"
        val actualRoute = Screen.AdditionalInformationScreen.route
        assertEquals(expectedRoute, actualRoute)
    }

    @Test
    fun testSuccessfulScreenRoute() {
        val expectedRoute = "Successful"
        val actualRoute = Screen.SuccessfulScreen.route
        assertEquals(expectedRoute, actualRoute)
    }
}
