package com.shepherd.employee.networking

import com.shepherd.employee.networking.data.Endpoints
import com.shepherd.employee.networking.data.request.AddEmployeeRequest
import com.shepherd.employee.networking.data.response.ColoursResponse
import com.shepherd.employee.networking.data.response.EmployeeResponse
import com.shepherd.employee.networking.data.response.LoginResponse
import com.shepherd.employee.networking.data.response.UserData
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface EmployeeNetworkService {
    @POST(Endpoints.LOGIN)
    suspend fun login(@Body body: RequestBody): Response<LoginResponse>

    @GET(Endpoints.USERS)
    suspend fun getUsers(): Response<UserData>

    @POST(Endpoints.USERS)
    suspend fun addEmployees(@Body body: AddEmployeeRequest): Response<Unit>

    @GET(Endpoints.USERS)
    suspend fun getEmployees(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): Response<EmployeeResponse>

    @GET(Endpoints.UNKNOWN)
    suspend fun getColours(
        @Query("per_page") perPage: Int,
    ): Response<ColoursResponse>
}
