package com.shepherd.employee.networking.data.response

import com.google.gson.annotations.SerializedName

data class Colour(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("year")
    val year: Int,
    @SerializedName("color")
    val color: String,
    @SerializedName("pantone_value")
    val pantoneValue: String
)
