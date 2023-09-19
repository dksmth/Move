package com.example.move.models

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

//@Parcelize
data class OneSet(
    var weight: Double,
    var reps: Int,
): java.io.Serializable {

    val oneRepMax: Double
        get() = calculateOneRepMax()

    // Brzycki one rep max formula for <10 reps and Epley one rep max formula for >10

    private fun calculateOneRepMax(): Double {
        return if (reps < 10) {
            weight * (BRYZCKI_FORMULA_NUMERATOR / (BRYZCKI_FORMULA_DENOMINATOR - reps))
        } else {
            weight * (1 + reps.toDouble() / EPLEY_FORMULA_DENOMINATOR)
        }
    }

    override fun toString(): String {
        return "Set(weight=$weight, reps=$reps)"
    }

    companion object {
        const val BRYZCKI_FORMULA_NUMERATOR = 36.0
        const val BRYZCKI_FORMULA_DENOMINATOR = 37.0
        const val EPLEY_FORMULA_DENOMINATOR = 30.0
    }
}
