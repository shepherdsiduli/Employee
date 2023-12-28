package com.shepherd.employee.networking.data.request

data class AddEmployeeRequest(
    val userLoginToken: String? = "",
    val personalDetails: PersonalDetails,
    val additionalInformation: AdditionalInformation,
)
