package com.shepherd.employee.repo

import com.shepherd.employee.networking.EmployeeNetworkService
import com.shepherd.employee.networking.data.response.ColoursResponse
import com.shepherd.employee.networking.data.response.EmployeeResponse
import com.shepherd.employee.networking.data.response.LoginResponse
import com.shepherd.employee.networking.data.response.Support
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

            whenever(repo.login(any())).thenReturn(
                Response.success(response),
            )
        }
    }

    @Test
    fun `verify getEmployees is invoked`() {
        runBlocking {
            val response = EmployeeResponse(
                page = 1,
                perPage = 1,
                total = 1,
                totalPages = 1,
                data = emptyList(),
                support = Support(
                    url = "https://reqres.in/#support-heading",
                    text = "To keep ReqRes free, contributions towards server costs are appreciated!",
                ),
            )

            whenever(repo.getEmployees(any(), any())).thenReturn(
                Response.success(response),
            )
        }
    }

    @Test
    fun `verify getColours is invoked`() {
        runBlocking {
            val response = ColoursResponse(
                page = 1,
                perPage = 1,
                total = 1,
                totalPages = 1,
                data = emptyList(),
                support = Support(
                    url = "https://reqres.in/#support-heading",
                    text = "To keep ReqRes free, contributions towards server costs are appreciated!",
                ),
            )

            whenever(repo.getColours(any())).thenReturn(
                Response.success(response),
            )
        }
    }
}
