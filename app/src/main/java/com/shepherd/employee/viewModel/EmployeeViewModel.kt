package com.shepherd.employee.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shepherd.employee.networking.data.Utilities.calendarToString
import com.shepherd.employee.networking.data.Utilities.getJsonRequestBody
import com.shepherd.employee.networking.data.request.AddEmployeeRequest
import com.shepherd.employee.networking.data.request.AdditionalInformation
import com.shepherd.employee.networking.data.request.PersonalDetails
import com.shepherd.employee.networking.data.response.Colour
import com.shepherd.employee.networking.data.response.Employee
import com.shepherd.employee.repo.EmployeeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.util.Calendar
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

    private var _submitInformationUiState = MutableStateFlow(SubmitInformationViewState())
    var submitInformationUiState: StateFlow<SubmitInformationViewState> = _submitInformationUiState.asStateFlow()

    var selectedColour: Colour? = null
    fun setColour(colour: Colour) {
        selectedColour = colour
    }

    var selectedEmployee: Employee? = null
    fun setEmployee(employee: Employee) {
        selectedEmployee = employee
    }

    var selectedDateBirth: Calendar = Calendar.getInstance()
    var selectedPlaceOfBirth: String = ""
    var selectedGender: String = ""
    var selectedResidential: String = ""

    var token: String = ""

    fun clearData() {
        selectedDateBirth = Calendar.getInstance()
        selectedGender = ""
        selectedResidential = ""
    }

    private fun getLoginRequest(username: String, password: String) = JSONObject().apply {
        put("username", username)
        put("password", password)
    }.getJsonRequestBody()

    fun login(username: String, password: String) {
        _loginUiState.update {
            it.copy(
                isLoading = true,
            )
        }
        val request = getLoginRequest(username = username, password = password)

        viewModelScope.launch {
            val result = repository.login(request)
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

    fun addEmployee(selectedEmployee: Employee) {
        viewModelScope.launch {
            _submitInformationUiState.update {
                it.copy(
                    isError = false,
                    isLoading = true,
                    isSuccess = false,
                )
            }

            val body = AddEmployeeRequest(
                userLoginToken = token,
                additionalInformation = AdditionalInformation(
                    placeOfBirth = selectedPlaceOfBirth,
                    preferredColor = selectedColour!!.color,
                    residential = selectedResidential,

                ),
                personalDetails = PersonalDetails(
                    id = selectedEmployee.id,
                    email = selectedEmployee.email,
                    first_Name = selectedEmployee.firstName,
                    last_name = selectedEmployee.lastName,
                    avatar = selectedEmployee.avatar,
                    DOB = calendarToString(selectedDateBirth),
                    gender = selectedGender.toString(),
                ),
            )

            try {
                val response = repository.addEmployees(body)
                if (response.isSuccessful) {
                    _submitInformationUiState.update {
                        it.copy(
                            isError = false,
                            isLoading = false,
                            isSuccess = true,
                        )
                    }
                } else {
                    _submitInformationUiState.update {
                        it.copy(
                            isError = true,
                            isLoading = false,
                            isSuccess = false,
                        )
                    }
                }
            } catch (e: Exception) {
                _submitInformationUiState.update {
                    it.copy(
                        isError = true,
                        isLoading = false,
                        isSuccess = false,
                    )
                }
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

    data class SubmitInformationViewState(
        val isLoading: Boolean = false,
        val isError: Boolean = false,
        val isSuccess: Boolean = false,
    )
}
