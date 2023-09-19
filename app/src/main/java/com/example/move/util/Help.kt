package com.example.move.util

import android.text.Editable
import android.text.TextWatcher
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

open class SimpleTextWatcher : TextWatcher {
    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    override fun afterTextChanged(s: Editable) {}
}