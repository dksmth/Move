package com.example.move.repo

import com.example.move.api.RetrofitInstance
import com.example.move.db.ExerciseDatabase
import com.example.move.models.ExerciseItem
import com.example.move.models.Workout

class ExercisesRepository(
    val db: ExerciseDatabase
) {

    suspend fun getExercises() = RetrofitInstance.api.getAllExercises()

    suspend fun upsert(exercise: ExerciseItem) = db.getExerciseDao().upsert(exercise = exercise)

    suspend fun upsertAll(exerciseList: List<ExerciseItem>) = db.getExerciseDao().upsertAll(exerciseList = exerciseList)

    suspend fun upsert(workout: Workout) = db.getExerciseDao().upsertWorkout(workout = workout)

    suspend fun getSavedExercises() = db.getExerciseDao().getAllExercises()

    suspend fun checkIfExists(): Boolean = db.getExerciseDao().isExists()

    suspend fun deleteAll() = db.getExerciseDao().deleteAll()
}