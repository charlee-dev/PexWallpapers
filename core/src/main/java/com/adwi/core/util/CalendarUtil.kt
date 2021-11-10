package com.adwi.core.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
object CalendarUtil {

    fun getTodayDate(): String {
        val now = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd-MM")
        return now.format(formatter)
    }

    fun getDayOfWeek() = LocalDate.now().dayOfWeek.name

    fun getCurrentHour(): String {
        val hours = Calendar.getInstance().get(Calendar.HOUR_OF_DAY).toString()
        return if (hours.toInt() < 10) "0$hours" else hours
    }

    fun getCurrentMinutes(): String {
        val minutes = Calendar.getInstance().get(Calendar.MINUTE).toString()
        return if (minutes.toInt() < 10) "0$minutes" else minutes
    }

    fun getDayOfMonthNumber() = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
}