package com.shepherd.employee.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shepherd.employee.networking.data.Utilities.getJsonRequestBody
import com.shepherd.employee.networking.data.response.Colour
import com.shepherd.employee.networking.data.response.Employee
import com.shepherd.employee.repo.EmployeeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class EmployeeViewModel @Inject constructor(
    private val repository: EmployeeRepository,
) : ViewModel() {

    private var _loginUiState = MutableStateFlow(ViewState())
    var loginUiState: StateFlow<ViewState> = _loginUiState.asStateFlow()

    private var _employeeListUiState = MutableStateFlow(EmployeeListViewState())
    var employeeListUiState: StateFlow<EmployeeListViewState> = _employeeListUiState.asStateFlow()

    private var _coloursListUiState = MutableStateFlow(ColoursListViewState())
    var coloursListUiState: StateFlow<ColoursListViewState> = _coloursListUiState.asStateFlow()

    var selectedColour: Colour? = null
    fun setColour(colour: Colour) {
        selectedColour = colour
    }

    var selectedEmployee: Employee? = null
    fun setEmployee(employee: Employee) {
        selectedEmployee = employee
    }

    var selectedDateBirth: String? = null
    var selectedGender: String? = null
    var selectedResidential: String? = null

    fun clearData() {
        selectedDateBirth = null
        selectedGender = null
        selectedResidential = null
        selectedEmployee = null
        selectedColour = null
    }

    fun login(username: String, password: String) {
        val username2 = "tracey.ramos@reqres.in1"
        val password2 = "test"
        _loginUiState.update {
            it.copy(
                isLoading = true,
            )
        }
        viewModelScope.launch(Dispatchers.IO) {
            val body: RequestBody = JSONObject().apply {
                put("username", username2)
                put("password", password2)
            }.getJsonRequestBody()

            val result = repository.login(body)
            if (result.isSuccessful) {
                _loginUiState.update {
                    it.copy(
                        isLoading = false,
                        isSuccess = true,
                        token = result.body()?.token,
                    )
                }
            } else {
                _loginUiState.update {
                    it.copy(
                        isLoading = false,
                        isSuccess = false,
                        isError = true,
                        error = "Invalid credentials entered",
                    )
                }
            }
        }
    }

    fun fetchEmployees() {
        viewModelScope.launch {
            _employeeListUiState.update {
                it.copy(
                    isError = false,
                    isLoading = true,
                    isSuccess = false,
                    employees = emptyList(),
                )
            }
            try {
                val response = repository.getEmployees(1, 12)
                if (response.isSuccessful) {
                    val employees = response.body()?.data ?: emptyList()
                    _employeeListUiState.update {
                        it.copy(
                            isError = false,
                            isLoading = false,
                            isSuccess = true,
                            employees = employees,
                        )
                    }
                } else {
                    _employeeListUiState.update {
                        it.copy(
                            isError = true,
                            isLoading = false,
                            isSuccess = false,
                            employees = emptyList(),
                        )
                    }
                }
            } catch (e: Exception) {
                _employeeListUiState.update {
                    it.copy(
                        isError = true,
                        isLoading = false,
                        isSuccess = false,
                        employees = emptyList(),
                    )
                }
            }
        }
    }

    fun fetchColours() {
        viewModelScope.launch {
            _coloursListUiState.update {
                it.copy(
                    isError = false,
                    isLoading = true,
                    isSuccess = false,
                    colours = emptyList(),
                )
            }
            try {
                val response = repository.getColours(12)
                if (response.isSuccessful) {
                    val colours = response.body()?.data ?: emptyList()
                    _coloursListUiState.update {
                        it.copy(
                            isError = false,
                            isLoading = false,
                            isSuccess = true,
                            // employees = listOf(Employee(1, "test@email.com", "test", "lastName", "avatar")),
                            colours = colours,
                        )
                    }
                } else {
                    _coloursListUiState.update {
                        it.copy(
                            isError = true,
                            isLoading = false,
                            isSuccess = false,
                            colours = emptyList(),
                        )
                    }
                }
            } catch (e: Exception) {
                // Handle network errors
            }
        }
    }

    fun addEmployee() {
        viewModelScope.launch {
            _employeeListUiState.update {
                it.copy(
                    isError = false,
                    isLoading = true,
                    isSuccess = false,
                    employees = emptyList(),
                )
            }
            try {
                val response = repository.getEmployees(1, 12)
                if (response.isSuccessful) {
                    val employees = response.body()?.data ?: emptyList()
                    _employeeListUiState.update {
                        it.copy(
                            isError = false,
                            isLoading = false,
                            isSuccess = true,
                            // employees = listOf(Employee(1, "test@email.com", "test", "lastName", "avatar")),
                            employees = employees,
                        )
                    }
                } else {
                    _employeeListUiState.update {
                        it.copy(
                            isError = true,
                            isLoading = false,
                            isSuccess = false,
                            employees = emptyList(),
                        )
                    }
                }
            } catch (e: Exception) {
                // Handle network errors
            }
        }
    }

    data class ViewState(
        val isLoading: Boolean = false,
        val isError: Boolean = false,
        val error: String? = null,
        val isSuccess: Boolean = false,
        val token: String? = null,
    )

    data class EmployeeListViewState(
        val employees: List<Employee> = emptyList(),
        val isLoading: Boolean = false,
        val isError: Boolean = false,
        val isSuccess: Boolean = false,
    )

    data class ColoursListViewState(
        val colours: List<Colour> = emptyList(),
        val isLoading: Boolean = false,
        val isError: Boolean = false,
        val isSuccess: Boolean = false,
    )
}
