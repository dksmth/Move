package com.example.move.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Block(
    var exercise: ExerciseItem? = null,
    var listOfSets: MutableList<OneSet> = mutableListOf(OneSet(0, 0)),
    val comment: String? = null,
) : Parcelable {
    override fun toString(): String {
        return this.exercise?.name.toString() + ":" + listOfSets.joinToString(separator = ";")
    }
}