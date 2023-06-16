package com.example.move.db

import androidx.room.TypeConverter
import com.example.move.models.*

class Converters {


//    @TypeConverter
//    fun fromStringToSet(string: String): OneSet {
//
//        val smth = string.substringAfter('=')
//        val weight = smth.substringBefore(',')
//        val reps = smth.substringAfter('=').replace(")", "")
//
//        return OneSet(weight.toInt(), reps.toInt())
//    }

    @TypeConverter
    fun fromListOfBlocksToString(list: List<Block>): String {
        return list.joinToString(separator = "/")
    }

    @TypeConverter
    fun fromStringToBlocks(str: String): List<Block> {

        val result = mutableListOf<Block>()

        if (str.isBlank()) return emptyList()

        val blocks = str.split("/")

        blocks.forEach { block ->

            val resultingBlock = Block()

            val nameAndSets = block.split(":")

            resultingBlock.exercise = ExerciseItem(name = nameAndSets[0])

            val sets = nameAndSets[1].split(";")

            val trueSets = mutableListOf<OneSet>()

            sets.forEach { oneSetString ->
                val weight = oneSetString.substringAfter("weight=").substringBefore(",")
                val reps = oneSetString.substringAfter("reps=").substringBefore(")")

                trueSets.add(OneSet(weight.toInt(), reps.toInt()))
            }

            resultingBlock.listOfSets = trueSets

            result.add(resultingBlock)
        }

        return result

    }

//    @TypeConverter
//    fun fromStringToWorkout(str: String): Workout {
//        return Workout(blocks = listOf(Block(comment = str)))
//    }
}