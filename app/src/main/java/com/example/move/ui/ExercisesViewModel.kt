package com.example.move.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.move.models.*
import com.example.move.repo.ExercisesRepository
import com.example.move.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class ExercisesViewModel(private val exercisesRepository: ExercisesRepository) : ViewModel() {

    val exercises: MutableLiveData<Resource<List<ExerciseItem>>> = MutableLiveData()

    init {
        getExercises()
    }

    private fun getExercises() {
        viewModelScope.launch {
            exercises.postValue(Resource.Loading())
            val response = exercisesRepository.getExercises()

            val resource = handleResponse(response)

            exercises.postValue(resource)
        }
    }

    private fun getExercisesFromDb() {
        viewModelScope.launch {
            exercises.postValue(Resource.Loading())
            val response = exercisesRepository.getSavedExercises()

            exercises.value = response.value?.let { Resource.Success(it) }!!
        }
    }

    private fun handleResponse(response: Response<List<ExerciseItem>>): Resource<List<ExerciseItem>> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

}