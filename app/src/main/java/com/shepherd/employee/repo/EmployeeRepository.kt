package com.shepherd.employee.repo

import com.shepherd.employee.networking.data.request.AddEmployeeRequest
import com.shepherd.employee.networking.data.response.ColoursResponse
import com.shepherd.employee.networking.data.response.EmployeeResponse
import com.shepherd.employee.networking.data.response.LoginResponse
import com.shepherd.employee.networking.data.response.UserData
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body

interface EmployeeRepository {
    suspend fun login(@Body body: RequestBody): Response<LoginResponse>
    suspend fun getUsers(): Response<UserData>
    suspend fun getEmployees(page: Int, perPage: Int): Response<EmployeeResponse>
    suspend fun getColours(perPage: Int): Response<ColoursResponse>
    suspend fun addEmployees(@Body body: AddEmployeeRequest): Response<Unit>
}
