package com.example.move.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.move.models.*
import com.example.move.repo.ExercisesRepository
import com.example.move.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class ExercisesViewModel(private val exercisesRepository: ExercisesRepository) : ViewModel() {

    val exercises: MutableLiveData<Resource<List<ExerciseItem>>> = MutableLiveData()

    init {
        viewModelScope.launch {
            if (exercisesRepository.checkIfExists()) {
                getExercisesFromDb()
            } else {
                getExercises()
            }
        }
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
            withContext(Dispatchers.IO) {
                exercises.postValue(Resource.Loading())
                val response = exercisesRepository.getSavedExercises()

                response.let {
                    exercises.postValue(Resource.Success(it))
                }
            }
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