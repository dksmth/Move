package com.example.move.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.move.repo.ExercisesRepository

class ExercisesViewModelProvideFactory(
    private val exercisesRepository: ExercisesRepository
    ): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ExercisesViewModel(exercisesRepository) as T
    }
}