package com.example.move.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OneSet(
    val weight: Int,
    val reps: Int
): Parcelable
