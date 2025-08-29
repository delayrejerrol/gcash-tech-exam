package com.jnda.openweatherapp.ext

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateExtension {
    private val sdf = SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH)
    private val dateFormat = SimpleDateFormat("EEE, MMM dd", Locale.ENGLISH)
    private val timeFormat = SimpleDateFormat("hh:mm a", Locale.ENGLISH)

    fun getFormattedDate(date: Long): String {
        val s: String = if (isDateToday(date)) {
            "Today, " + timeFormat.format(Date(date))
        } else {
            dateFormat.format(Date(date))
        }
        return s
    }

    private fun isDateToday(oldDate: Long) : Boolean {
        val old = sdf.format(Date(oldDate))
        val current = sdf.format(Date())
        return old == current
    }
}