package com.example.move.util

import java.util.*


fun onlyFirstCharCapitalized(string: String): String =
    string.lowercase().replaceFirstChar { it.uppercase() }

fun String.trimLastIf(predicate: String): String {
    return if (this.takeLast(predicate.length) == predicate) this.dropLast(predicate.length) else
        this
}

fun String.capitalized(): String = replaceFirstChar {
    if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
}

fun Double.roundToDecimal(digits: Int): String = "%.${digits}f".format(this)

fun Double.parseWeight(): String =
    roundToDecimal(1).trimLastIf(",0").replace(',', '.')