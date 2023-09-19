package com.example.move.models

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class WorkoutWithBlocks(
    @Embedded val workout: Workout,
    @Relation(
        parentColumn = "workout_id",
        entityColumn = "block_id",
        associateBy = Junction(WorkoutBlockCrossRef::class)
    )
    val blocks: List<Block>
)
