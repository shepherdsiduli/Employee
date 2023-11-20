package com.shepherd.employee.networking.data

import android.app.DatePickerDialog
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object Utilities {
    fun Any.getJsonRequestBody() = this.toString().toRequestBody("application/json".toMediaTypeOrNull())

    fun showDatePicker(
        context: android.content.Context,
        selectedDate: Calendar,
        onDateSelected: (Calendar) -> Unit,
    ) {
        val datePicker = DatePickerDialog(
            context,
            { _, year, month, day ->
                selectedDate.set(year, month, day)
                onDateSelected(selectedDate)
            },
            selectedDate.get(Calendar.YEAR),
            selectedDate.get(Calendar.MONTH),
            selectedDate.get(Calendar.DAY_OF_MONTH),
        )
        datePicker.show()
    }

    fun calendarToString(calendar: Calendar): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }
}
