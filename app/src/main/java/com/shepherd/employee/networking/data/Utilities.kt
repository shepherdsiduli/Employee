package com.shepherd.employee.networking.data

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

object Utilities {
    fun Any.getJsonRequestBody() = this.toString().toRequestBody("application/json".toMediaTypeOrNull())
}
