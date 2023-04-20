package com.example.move.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.move.models.Block
import com.example.move.models.Exercise
import com.example.move.models.ExerciseItem
import com.example.move.models.Workout

class WorkoutViewModel: ViewModel() {

    var workout: MutableLiveData<Workout> = MutableLiveData(Workout())

//    fun addExercise(exercise: ExerciseItem) {
//        if (workout.value != null) {
//            workout.value!!.exercises?.plusAssign(exercise)
//        } else {
//            workout.value = Workout(arrayListOf(exercise))
//        }
//    }
//
//    fun addSet(block: Block) {
//        workout.value?.exercises
//    }
}