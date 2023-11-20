package com.shepherd.employee.repo

import com.shepherd.employee.networking.EmployeeNetworkService
import com.shepherd.employee.networking.data.request.AddEmployeeRequest
import com.shepherd.employee.networking.data.response.ColoursResponse
import com.shepherd.employee.networking.data.response.EmployeeResponse
import com.shepherd.employee.networking.data.response.LoginResponse
import com.shepherd.employee.networking.data.response.UserData
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import javax.inject.Inject

class EmployeeRepoImpl @Inject constructor(
    private val networkService: EmployeeNetworkService,
) : EmployeeRepository {

    override suspend fun login(@Body body: RequestBody): Response<LoginResponse> {
        return networkService.login(
            body = body,
        )
    }

    override suspend fun getUsers(): Response<UserData> {
        return networkService.getUsers()
    }

    override suspend fun getEmployees(
        page: Int,
        perPage: Int,
    ): Response<EmployeeResponse> {
        return networkService.getEmployees(page, perPage)
    }

    override suspend fun getColours(perPage: Int): Response<ColoursResponse> {
        return networkService.getColours(perPage)
    }

    override suspend fun addEmployees(request: AddEmployeeRequest): Response<LoginResponse> {
        return networkService.addEmployees(request)
    }
}
