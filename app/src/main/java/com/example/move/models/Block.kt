package com.example.move.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Block (
        val exercise: ExerciseItem? = null,
        val listOfSets: MutableList<OneSet> = mutableListOf(),
        val comment: String? = null
        ): Parcelable