package com.example.move.ui.viewmodels

import android.app.Application
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
        if (workout.value?.isNotEmpty() == true) {
            workout.value = workout.value?.plus(block)
        } else {
            workout.value = listOf(block)
        }
    }

    fun addSet(block: Block) {
        val chosenBlock = workout.value?.find { it == block }

        chosenBlock!!.listOfSets = chosenBlock.listOfSets + OneSet(0, 0)
    }

    /*
    workout.value?.find { it == block }?.listOfSets = workout.value?.find { it == block }?.listOfSets?.plus(
            OneSet(0,0)
        )!!

    sets.value = workout.value?.find { it == block }?.listOfSets
     */

    fun deleteExercise(block: Block) {
        workout.value = workout.value?.minus(block)
    }

    fun endWorkout() {
        workout.value = listOf()
    }

    fun canBeFinished(): Boolean {
        return workout.value?.isNotEmpty() ?: false
    }

    suspend fun insertWorkout() {
        dao.upsertWorkout(workout = Workout(blocks = workout.value))
    }


}