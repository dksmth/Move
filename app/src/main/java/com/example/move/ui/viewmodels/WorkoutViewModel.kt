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

    fun changeSet(block: Block, str: String) {

        Log.d("changeSet", block.toString() + str)

        val (value, position) = parseString(str)

        val chosenBlock = workout.value?.find { it == block }

        Log.d("changeSet", chosenBlock.toString())

        chosenBlock!!.listOfSets[position] = OneSet(value, value)

        // workout.value?.last()?.listOfSets!![position] = OneSet(value, value)

    }

    fun parseString(str: String): Pair<Int, Int> {

        // Нужно лучше запарсить

        val smth = str.split(' ')

        Log.d("WorkoutVM", smth.toString())

        var first = str.substringBefore(' ')
        if (first == "") {
            first = "0"
        }
        val second = str.substringAfter(' ')
        return Pair(first.toInt(), second.toInt())
    }

    suspend fun insertWorkout() {
        dao.upsertWorkout(workout = Workout(blocks = workout.value))
    }

}