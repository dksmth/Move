package com.example.move.ui.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.move.db.ExerciseDao
import com.example.move.db.ExerciseDatabase
import com.example.move.models.Block
import com.example.move.models.OneSet
import com.example.move.models.Workout

class WorkoutViewModel(application: Application) : AndroidViewModel(application) {

    var workout: MutableLiveData<List<Block>> = MutableLiveData(listOf())

    private var dao: ExerciseDao = ExerciseDatabase(application).getExerciseDao()

    fun addExercise(block: Block) {
        workout.value = workout.value?.plus(block)
    }

    fun addSet(block: Block) {
        val chosenBlock = workout.value?.find { it == block }

        chosenBlock!!.listOfSets = (chosenBlock.listOfSets + OneSet(0, 0)) as MutableList<OneSet>
    }

    fun deleteExercise(block: Block) {
        workout.value = workout.value?.minus(block)
    }

    fun endWorkout() {
        workout.value = listOf()
    }

    fun canBeFinished(): Boolean {
        return workout.value?.isNotEmpty() ?: false
    }

    // Нихрена не заработало

    fun changeSet(block: Block, strings: List<String>) {

        val (value, position, type) = strings

        var returnedNumber= 0.0

        if (value.isNotBlank()) {
            returnedNumber = value.toDouble()
            if (returnedNumber > 1000) {
                returnedNumber = 1000.0
            }
        }

        val chosenBlock = workout.value?.find { it == block }

        if (type == "weight") {
            chosenBlock!!.listOfSets[position.toInt()].weight = returnedNumber.toInt()
        } else {
            chosenBlock!!.listOfSets[position.toInt()].reps = returnedNumber.toInt()
        }


        // workout.value?.last()?.listOfSets!![position] = OneSet(value, value)

    }

    fun getWorkoutInfo(): String {
        return workout.value.toString()
    }


    suspend fun insertWorkout() {
        dao.upsertWorkout(workout = Workout(blocks = workout.value))
    }

}