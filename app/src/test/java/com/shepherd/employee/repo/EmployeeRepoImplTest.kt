package com.shepherd.employee.repo

import com.shepherd.employee.networking.EmployeeNetworkService
import com.shepherd.employee.networking.data.response.LoginResponse
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import retrofit2.Response

class EmployeeRepoImplTest {
    @Mock
    private lateinit var mockNetworkService: EmployeeNetworkService

    private lateinit var repo: EmployeeRepoImpl

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repo = EmployeeRepoImpl(mockNetworkService)
    }

    @Test
    fun `verify login is invoked`() {
        runBlocking {
            val response = LoginResponse(
                token = "QpwL5tke4Pnpja7X6",

            )
            val username = "tracey.ramos@reqres.in"
            val password = "test"

            whenever(repo.login(any())).thenReturn(
                Response.success(response),
            )
        }
    }

    //  @Test
    fun `verify login is invoked with error`() {
        runBlocking {
            val response = LoginResponse(
                token = "QpwL5tke4Pnpja7X6",

            )
            val username = "tracey.ramos@reqres.in"
            val password = "test"

            whenever(repo.login(any())).thenReturn(
                Response.error(400, any()),
            )
        }
    }
}
