package com.example.move.ui.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.move.models.Block
import com.example.move.models.Workout
import com.example.move.repo.ExercisesRepository

class WorkoutHistoryViewModel(private val exercisesRepository: ExercisesRepository) : ViewModel() {

    val workouts: MutableLiveData<List<Workout>> = MutableLiveData()

    val mapWorkoutToBlocks: MutableLiveData<Map<Workout, List<Block>>> = MutableLiveData()


    suspend fun getAllWorkouts() {
        val allWorkouts = exercisesRepository.getAllWorkouts()

        Log.d("Workouts", allWorkouts.toString())

        val map = exercisesRepository.readBlocks()

        mapWorkoutToBlocks.postValue(map)

        Log.d("Map", map.toString())

        workouts.postValue(allWorkouts)
    }

}