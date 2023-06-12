package com.example.move.db

import androidx.room.TypeConverter
import com.example.move.models.Block
import com.example.move.models.Workout

class Converters {

    @TypeConverter
    fun fromListOfBlocksToString(list: List<Block>): String {
        return list.toString()
    }

    @TypeConverter
    fun fromStringToBlocks(str: String): List<Block> {
        return listOf<Block>()
    }

    @TypeConverter
    fun fromStringToWorkout(str: String): Workout {
        return Workout(blocks = listOf(Block(comment = str)))
    }
}