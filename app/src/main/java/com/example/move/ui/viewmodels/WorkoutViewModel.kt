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
    fun deleteExercise(block: Block) {
        workout.value = workout.value?.minus(block)
    }

    fun addSet(block: Block) {
        val chosenBlock = workout.value?.find { it == block }

        chosenBlock!!.listOfSets = (chosenBlock.listOfSets + OneSet(0, 0)) as MutableList<OneSet>
    }

    fun changeSet(block: Block, strings: List<String>) {

        val (value, position, type) = strings

        var returnedNumber = 0.0

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
    }

    fun deleteSet(block: Block, position: Int) {
        val chosenBlock = workout.value?.find { it == block }

        chosenBlock!!.listOfSets.removeAt(position)
    }

    fun endWorkout() {
        workout.value = listOf()
    }

    fun canBeFinished(): Boolean {
        return workout.value?.isNotEmpty() ?: false
    }

    fun getWorkoutInfo(): String {
        return workout.value.toString()
    }

    suspend fun insertWorkout() {
        Log.d("BeforeInserting", workout.value.toString())

        var check = workout.value

        val smth = dao.upsertWorkout(workout = Workout(blocks = check))

        Log.d("AfterInserting", workout.value.toString())

        check?.forEach {
            it.workout_id = smth.toInt()
        }

        Log.d("BeforeSaving", workout.value.toString())

        check?.forEach {
            dao.insertBlocks(it)
        }
    }
}