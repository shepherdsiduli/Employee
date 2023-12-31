package com.shepherd.employee.networking.data.response

import com.google.gson.annotations.SerializedName

data class ColoursResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("per_page")
    val perPage: Int,
    @SerializedName("total")
    val total: Int,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("data")
    val data: List<Colour>,
    @SerializedName("support")
    val support: Support
)
