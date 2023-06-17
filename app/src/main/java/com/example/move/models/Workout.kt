package com.example.move.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "workouts"
)
data class Workout (
    @PrimaryKey(autoGenerate = true)
    var workout_id: Int? = null,
    var blocks: List<Block>? = null,
    var dateTime: String
    )