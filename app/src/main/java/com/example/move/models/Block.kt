package com.example.move.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Block (
        val exercise: ExerciseItem? = null,
        var listOfSets: List<OneSet> = listOf(),
        val comment: String? = null
        ): Parcelable