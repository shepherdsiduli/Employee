package com.shepherd.employee.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.viewModelScope
import com.shepherd.employee.networking.data.response.LoginResponse
import com.shepherd.employee.repo.EmployeeRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import retrofit2.Response

@ExperimentalCoroutinesApi
class EmployeeViewModelTest {
    private val testDispatcher = TestCoroutineDispatcher()

    private val testScope = TestCoroutineScope(testDispatcher)

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var mockRepository: EmployeeRepository

    private lateinit var employeeViewModel: EmployeeViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)

        employeeViewModel = EmployeeViewModel(mockRepository).apply {
          //  this.viewModelScope = testScope
        }
    }

    @Test
    fun `test login with success`() = testScope.runBlockingTest {
        val mockedToken = "mocked_token"
        val successResponse = Response.success(LoginResponse(mockedToken))
        `when`(mockRepository.login(any())).thenReturn(successResponse)

        employeeViewModel.login("username", "password")

        val uiState = employeeViewModel.loginUiState.value
        assert(uiState.isLoading)
        testDispatcher.scheduler.advanceUntilIdle()
        assert(!uiState.isLoading)
        assert(uiState.isSuccess)
        assert(uiState.token == mockedToken)
    }
}