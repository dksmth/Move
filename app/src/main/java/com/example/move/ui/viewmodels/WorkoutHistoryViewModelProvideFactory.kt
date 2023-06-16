package com.example.move.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.move.repo.ExercisesRepository

class WorkoutHistoryViewModelProvideFactory(private val exercisesRepository: ExercisesRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WorkoutHistoryViewModel(exercisesRepository) as T
    }
}