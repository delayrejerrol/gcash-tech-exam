package com.jnda.openweatherapp.ext

import java.util.Locale

fun String?.toTitleCase() : String {
    if (this.isNullOrEmpty()) {
        return ""
    }
    return this.split(" ").joinToString(" ") { ss ->
        ss.lowercase().replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.US) else it.toString() }
    }
}