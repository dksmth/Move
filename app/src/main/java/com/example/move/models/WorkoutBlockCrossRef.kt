package com.example.move.models

import androidx.room.Entity

@Entity(primaryKeys = ["workout_id","block_id"])
data class WorkoutBlockCrossRef(
    val workout_id: Long,
    val block_id: Long
)