package com.example.move.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.move.models.Block
import com.example.move.models.BlocksWithDateTime
import com.example.move.models.ExerciseItem
import com.example.move.repo.ExercisesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WorkoutHistoryViewModel(private val exercisesRepository: ExercisesRepository) : ViewModel() {

    val mapWorkoutToBlocks: MutableLiveData<List<BlocksWithDateTime>> = MutableLiveData()

    val historyOfExercise: MutableLiveData<List<Block>> = MutableLiveData()

    fun getAllWorkouts() {
        viewModelScope.launch(Dispatchers.IO) {
            val workoutsWithBlocks = exercisesRepository.getWorkoutsWithBlocks()
            val blocksWithDateTimes = workoutsWithBlocks.map { BlocksWithDateTime(it.workout.dateTime, it.blocks) }

            mapWorkoutToBlocks.postValue(blocksWithDateTimes.reversed())
        }
    }

    fun getExerciseHistory(exerciseItem: ExerciseItem) {
        viewModelScope.launch(Dispatchers.IO) {
            val listOfExerciseBlocks = exercisesRepository.getBlocksByExercise(exerciseItem)

            historyOfExercise.postValue(listOfExerciseBlocks.reversed())
        }
    }

}