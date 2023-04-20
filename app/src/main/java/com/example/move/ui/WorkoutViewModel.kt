package com.example.move.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.move.models.Block
import com.example.move.models.OneSet

class WorkoutViewModel : ViewModel() {

    var workout: MutableLiveData<List<Block>> = MutableLiveData(listOf())

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

        workout.value = workout.value?.minus(block)

        workout.value = workout.value?.plus(newBlock)
    }

}