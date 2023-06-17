package com.example.move.db

import androidx.room.TypeConverter
import com.example.move.models.Block
import com.example.move.models.ExerciseItem
import com.example.move.models.OneSet
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun fromExercise(exercise: ExerciseItem): String {
        return exercise.toString()
    }

    @TypeConverter
    fun toExercise(str: String): ExerciseItem {
        return ExerciseItem()
    }

    @TypeConverter
    fun fromOneSet(set: List<OneSet>): String {
        return set.toString()
    }

    @TypeConverter
    fun toOneSet(str: String): List<OneSet> {
        return listOf(OneSet(0.0,0))
    }

    @TypeConverter
    fun fromBlockToString(list: List<Block>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromStringToBlocks(str: String): List<Block> {

        val listType = object : TypeToken<List<Block>>() {}.type

        return gson.fromJson(str, listType)

    }

}