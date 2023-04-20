package com.example.move.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.move.models.*
import com.example.move.repo.ExercisesRepository
import com.example.move.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class ExercisesViewModel(val exercisesRepository: ExercisesRepository): ViewModel() {

    val exercises: MutableLiveData<Resource<List<ExerciseItem>>> = MutableLiveData()
    val exercisesPage = 1

    var workout: MutableLiveData<List<Block>> = MutableLiveData(listOf())

    var newWorkout: MutableLiveData<Workout> = MutableLiveData(Workout())


    fun addExercise(block: Block) {
        if (workout.value?.isNotEmpty() == true) {
            workout.value = workout.value?.plus(block)
        } else {
            workout.value = listOf(block)
        }
    }

    fun addSet(block: Block) {

        val newBlock = block.copy(
            listOfSets = (block.listOfSets + OneSet(0, 0)) as MutableList<OneSet>
        )

//        workout.value = workout.value?.filter { it == block }.forEach {
//            it = newBlock
//        }

//        newWorkout.value = newWorkout.value.copy(
//            blocks = newWorkout.value.blocks.forEach {
//                if (it == block) {
//                    it = newBlock
//                }
//            }
//        )
//
        workout.value = workout.value?.minus(block)

        workout.value = workout.value?.plus(newBlock)
    }

    init {
        getExercises()
    }


    fun getExercises() {
        viewModelScope.launch {
            exercises.postValue(Resource.Loading())
            val response = exercisesRepository.getExercises()

            val resource = handleResponse(response)

            exercises.postValue(resource)
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