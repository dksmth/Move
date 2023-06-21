package com.example.move.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.move.models.ExerciseItem
import com.example.move.repo.ExercisesRepository
import com.example.move.util.Resource
import com.example.move.util.capitalized
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class ExercisesViewModel(private val exercisesRepository: ExercisesRepository) : ViewModel() {

    val exercises: MutableLiveData<Resource<List<ExerciseItem>>> = MutableLiveData()

    init {
        viewModelScope.launch {
            if (exercisesRepository.cacheExists()) {
                getExercisesFromDb()
            } else {
                getExercises()
            }
        }
    }

    private fun getExercises() {
        viewModelScope.launch(context = Dispatchers.IO) {

            exercises.postValue(Resource.Loading())
            val response = exercisesRepository.getExercises()

            val resource = handleResponse(response)

            resource.apply {
                data?.map {
                    it.name = parseNames(it.name)
                }
            }

            exercises.postValue(resource)
        }
    }

    private fun getExercisesFromDb() {
        viewModelScope.launch(context = Dispatchers.IO) {

            exercises.postValue(Resource.Loading())
            val response = exercisesRepository.getSavedExercises()

            response.let { list ->
                list.map { exercise ->
                    exercise.apply {
                        exercise.name = parseNames(exercise.name)
                    }
                }

                exercises.postValue(Resource.Success(list))
            }
        }
    }

    private fun parseNames(str: String): String {
        return str.split(" ").joinToString(separator = " ") { it.capitalized() }
    }

    fun filter(str: String): List<ExerciseItem> =
        exercises.value?.data?.filter { it.name.lowercase().contains(str.lowercase()) }!!


    private fun handleResponse(response: Response<List<ExerciseItem>>): Resource<List<ExerciseItem>> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }
}