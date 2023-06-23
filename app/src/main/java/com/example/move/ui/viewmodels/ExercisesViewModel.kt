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

class ExercisesViewModel(private val exercisesRepository: ExercisesRepository) : ViewModel() {

    val exercises: MutableLiveData<Resource<List<ExerciseItem>>> = MutableLiveData()


    fun getExercisesFromDb() {
        if (exercises.value?.data == null) {

            viewModelScope.launch(context = Dispatchers.IO) {

                exercises.postValue(Resource.Loading())
                val response = exercisesRepository.getSavedExercises()

                exercises.postValue(Resource.Success(changeNames(response)))
            }
        }
    }

    private fun changeNames(list: List<ExerciseItem>): List<ExerciseItem> {
        return list.map { exercise ->
            exercise.apply {
                exercise.name = parseNames(exercise.name)
            }
        }
    }

    private fun parseNames(str: String): String {
        return str.split(" ").joinToString(separator = " ") { it.capitalized() }
    }

    fun filter(str: String): List<ExerciseItem> =
        exercises.value?.data?.filter { it.name.lowercase().contains(str.lowercase()) }!!

}