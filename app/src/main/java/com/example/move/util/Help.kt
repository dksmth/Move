package com.example.move.util



fun onlyFirstCharCapitalized(string: String): String =
        string.lowercase().replaceFirstChar { it.uppercase() }

fun String.trimLastIf(predicate: String): String {
        return if (this.takeLast(predicate.length) == predicate) this.dropLast(predicate.length) else
                this
}

fun Double.roundToDecimal(digits: Int): String = "%.${digits}f".format(this)