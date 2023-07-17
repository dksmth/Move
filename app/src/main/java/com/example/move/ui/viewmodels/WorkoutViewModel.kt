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
import com.example.move.models.WorkoutBlockCrossRef
import com.example.move.util.onlyFirstCharCapitalized
import java.time.LocalDate

class WorkoutViewModel(application: Application) : AndroidViewModel(application) {

    var _workout: MutableLiveData<List<Block>> = MutableLiveData(listOf())
    val workout: List<Block>?
        get() = _workout.value

    private var dao: ExerciseDao = ExerciseDatabase(application).getExerciseDao()

    var openedForResult = false

    fun addExercise(exercise: ExerciseItem) {
        val time = getWeekdayAndDate()
        _workout.value = _workout.value?.plus(Block(exercise = exercise, dateTime = time))

        setFlagForOpeningWithResult(false)
    }

    fun deleteExercise(block: Block) {
        _workout.value = _workout.value?.minus(block)
    }

    fun addSet(block: Block) {
        val chosenBlock = _workout.value?.find { it == block }

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

        val chosenBlock = _workout.value?.find { it == block }

        if (type == "weight") {
            chosenBlock!!.listOfSets[position.toInt()].weight = returnedNumber
        } else {
            chosenBlock!!.listOfSets[position.toInt()].reps = returnedNumber.toInt()
        }
    }

    fun deleteSet(block: Block, position: Int) {
        val chosenBlock = _workout.value?.find { it == block }

        chosenBlock!!.listOfSets.removeAt(position)
    }

    fun endWorkout() {
        _workout.value = listOf()
    }

    fun canBeFinished(): Boolean = workout?.isNotEmpty() ?: false

    fun getWorkoutInfo(): String = workout.toString()

    fun isInWorkout(exercise: ExerciseItem): Boolean = workout?.any { it.exercise == exercise }!!

    suspend fun insertWorkout() {
        val blocksToSave = _workout.value
        val time = getWeekdayAndDate()

        val workoutId = dao.upsertWorkout(workout = Workout(dateTime = time))

        blocksToSave?.forEach {
            val blockId = dao.insertBlock(it)
            val newCrossRef = WorkoutBlockCrossRef(workout_id = workoutId, block_id = blockId)

            dao.insertCrossReference(newCrossRef)
        }
    }

    private fun getWeekdayAndDate(): String {
        with(LocalDate.now()) {
            return onlyFirstCharCapitalized(dayOfWeek.toString()) +
                    ", " + onlyFirstCharCapitalized(month.toString()) + " " + dayOfMonth
        }
    }
}