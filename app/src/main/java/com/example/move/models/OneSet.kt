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

    private fun calculateOneRepMax(): Double {
        return if (reps < 10) {
            weight * (36.0 / (37.0 - reps))
        } else {
            weight * (1 + reps.toDouble() / 30.0)
        }
    }

    override fun toString(): String {
        return "Set(weight=$weight, reps=$reps)"
    }

}
