package com.example.move.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.move.db.ExerciseDao
import com.example.move.db.ExerciseDatabase
import com.example.move.models.Block
import com.example.move.models.ExerciseItem
import com.example.move.models.OneSet
import com.example.move.models.Workout
import com.example.move.util.onlyFirstCharCapitalized
import java.time.LocalDate

class WorkoutViewModel(application: Application) : AndroidViewModel(application) {

    var workout: MutableLiveData<List<Block>> = MutableLiveData(listOf())

    private var dao: ExerciseDao = ExerciseDatabase(application).getExerciseDao()

    var openedForResult = false

    fun addExercise(exercise: ExerciseItem) {
        workout.value = workout.value?.plus(Block(exercise = exercise))

        setFlagForOpeningWithResult(false)
    }

    fun deleteExercise(block: Block) {
        workout.value = workout.value?.minus(block)
    }

    fun addSet(block: Block) {
        val chosenBlock = workout.value?.find { it == block }

        chosenBlock!!.listOfSets = (chosenBlock.listOfSets + OneSet(0.0, 0)) as MutableList<OneSet>
    }

    fun setFlagForOpeningWithResult(opened: Boolean) {
        openedForResult = opened
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
            chosenBlock!!.listOfSets[position.toInt()].weight = returnedNumber
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

    fun isInWorkout(exercise: ExerciseItem): Boolean =
        workout.value?.any { it.exercise == exercise }!!

    suspend fun insertWorkout() {

        val check = workout.value

        val time = getWeekdayAndDate()

        val workoutId = dao.upsertWorkout(workout = Workout(blocks = check, dateTime = time))

        check?.forEach {
            it.workout_id = workoutId.toInt()
            dao.insertBlocks(it)
        }
    }

    private fun getWeekdayAndDate(): String {
        with(LocalDate.now()) {
            return onlyFirstCharCapitalized(dayOfWeek.toString()) +
                    ", " + onlyFirstCharCapitalized(month.toString()) + " " + dayOfMonth
        }
    }
}