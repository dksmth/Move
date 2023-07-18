package com.example.move.data.local

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
        return gson.toJson(exercise)
    }

    @TypeConverter
    fun toExercise(str: String): ExerciseItem {
        val type = object : TypeToken<ExerciseItem>() {}.type

        return gson.fromJson(str, type)
    }

    @TypeConverter
    fun fromOneSet(set: List<OneSet>): String {
        return gson.toJson(set)
    }

    @TypeConverter
    fun toOneSet(str: String): List<OneSet> {

        val listType = object : TypeToken<List<OneSet>>() {}.type

        return gson.fromJson(str, listType)
    }

//    @TypeConverter
//    fun fromBlockToString(list: List<Block>): String {
//        return gson.toJson(list)
//    }
//
//    @TypeConverter
//    fun fromStringToBlocks(str: String): List<Block> {
//
//        val listType = object : TypeToken<List<Block>>() {}.type
//
//        return gson.fromJson(str, listType)
//
//    }

}