package com.example.move.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.move.models.Block
import com.example.move.models.ExerciseItem
import com.example.move.models.OneSet
import com.example.move.models.Workout
import com.example.move.models.WorkoutBlockCrossRef
import com.example.move.repo.ExercisesRepository
import com.example.move.util.onlyFirstCharCapitalized
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class WorkoutViewModel @Inject constructor(private val exercisesRepository: ExercisesRepository) :
    ViewModel() {

    private val _workout = MutableLiveData<List<Block>>()
    val workout: LiveData<List<Block>>
        get() = _workout

    var openedForResult = false

    fun addExercise(exercise: ExerciseItem) {
        val time = getWeekdayAndDate()

        _workout.value = if (_workout.value == null) {
            listOf(Block(exercise = exercise, dateTime = time))
        } else {
            _workout.value?.plus(Block(exercise = exercise, dateTime = time))
        }

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
            returnedNumber = if (value.toDouble() > 1000) 1000.0 else value.toDouble()
        }

        val chosenBlock = _workout.value?.find { it == block }

        if (type == "weight") {
            chosenBlock?.let { it.listOfSets[position.toInt()].weight = returnedNumber }
        } else {
            chosenBlock?.let { it.listOfSets[position.toInt()].reps = returnedNumber.toInt() }
        }
    }

    fun deleteSet(block: Block, position: Int) {
        val chosenBlock = _workout.value?.find { it == block }

        chosenBlock!!.listOfSets.removeAt(position)
    }

    fun endWorkout() {
        _workout.value = listOf()
    }

    fun canBeFinished(): Boolean {
        val blocksList = workout.value

        return blocksList?.isNotEmpty() ?: false
                && blocksList?.none { it.listOfSets.isEmpty() } ?: false
                && blocksList?.none { block -> block.listOfSets.any { set -> set.reps == 0 || set.weight == 0.0 } } == true
    }

    fun getWorkoutInfo(): String = workout.toString()

    fun isInWorkout(exercise: ExerciseItem): Boolean =
        workout.value?.any { it.exercise == exercise } == true

    suspend fun insertWorkout() {
        val blocksToSave = _workout.value
        val time = getWeekdayAndDate()

        val workoutId = exercisesRepository.insertWorkout(Workout(dateTime = time))

        blocksToSave?.forEach {
            val blockId = exercisesRepository.insertBlocks(it)
            val newCrossRef = WorkoutBlockCrossRef(workout_id = workoutId, block_id = blockId)

            exercisesRepository.insertCrossReference(newCrossRef)
        }
    }

    private fun getWeekdayAndDate(): String {
        with(LocalDate.now()) {
            return onlyFirstCharCapitalized(dayOfWeek.toString()) +
                    ", " + onlyFirstCharCapitalized(month.toString()) + " " + dayOfMonth
        }
    }
}