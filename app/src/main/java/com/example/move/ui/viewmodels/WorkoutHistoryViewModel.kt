package com.example.move.ui.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.move.models.Workout
import com.example.move.repo.ExercisesRepository

class WorkoutHistoryViewModel(private val exercisesRepository: ExercisesRepository) : ViewModel() {

    val workouts: MutableLiveData<List<Workout>> = MutableLiveData()


    suspend fun getAllWorkouts() {
        val allWorkouts = exercisesRepository.getAllWorkouts()

        Log.d("Workouts", allWorkouts.toString())

        workouts.postValue(allWorkouts)
    }

}