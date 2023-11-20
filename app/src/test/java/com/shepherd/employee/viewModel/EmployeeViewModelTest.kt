package com.shepherd.employee.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.shepherd.employee.networking.data.response.LoginResponse
import com.shepherd.employee.repo.EmployeeRepoImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.spy
import org.mockito.kotlin.whenever
import retrofit2.Response

@ExperimentalCoroutinesApi
class EmployeeViewModelTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repo: EmployeeRepoImpl

    private lateinit var viewModel: EmployeeViewModel

    private lateinit var spyViewModel: EmployeeViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = EmployeeViewModel(repo)
        spyViewModel = spy(viewModel)
    }

    @Test
    fun `login success`() {
        runBlocking {
            val response = LoginResponse(
                token = "QpwL5tke4Pnpja7X6",

            )
            whenever(repo.login(any())).thenReturn(
                Response.success(response),
            )
        }
    }
}
