package com.shepherd.employee.di

import com.shepherd.employee.networking.EmployeeNetworkService
import com.shepherd.employee.networking.data.Endpoints.BASE_URL
import com.shepherd.employee.repo.EmployeeRepoImpl
import com.shepherd.employee.repo.EmployeeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideEmployeeService(): EmployeeNetworkService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(EmployeeNetworkService::class.java)

    @Singleton
    @Provides
    fun provideEmployeeRepo(networkService: EmployeeNetworkService): EmployeeRepository = EmployeeRepoImpl(networkService)
}
