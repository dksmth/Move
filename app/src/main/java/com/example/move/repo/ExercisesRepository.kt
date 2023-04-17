package com.example.move.repo

import com.example.move.api.RetrofitInstance
import com.example.move.db.ExerciseDatabase
import com.example.move.models.ExerciseItem

class ExercisesRepository(
    val db: ExerciseDatabase
) {

    suspend fun getExercises() = RetrofitInstance.api.getAllExercises()

    suspend fun upsert(exercise: ExerciseItem) = db.getExerciseDao().upsert(exercise = exercise)

    fun getSavedExercises() = db.getExerciseDao().getAllExercises()
}