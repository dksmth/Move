package com.example.move.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.move.models.Block
import com.example.move.models.Workout
import com.example.move.repo.ExercisesRepository

class WorkoutHistoryViewModel(private val exercisesRepository: ExercisesRepository) : ViewModel() {

    val mapWorkoutToBlocks: MutableLiveData<Map<Workout, List<Block>>> = MutableLiveData()

    suspend fun getAllWorkouts() {
        val map = exercisesRepository.readBlocks()

        mapWorkoutToBlocks.postValue(map)
    }

}