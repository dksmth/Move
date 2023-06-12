package com.example.move.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OneSet(
    var weight: Int,
    var reps: Int
): Parcelable
