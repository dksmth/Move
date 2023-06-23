package com.example.move.repo

import com.example.move.api.RetrofitInstance
import com.example.move.db.ExerciseDatabase
import com.example.move.models.Block
import com.example.move.models.ExerciseItem
import com.example.move.models.Workout

class ExercisesRepository(
    val db: ExerciseDatabase
) {

    suspend fun getExercisesFromApi() = RetrofitInstance.api.getAllExercises()

    suspend fun upsert(exercise: ExerciseItem) = db.getExerciseDao().upsert(exercise = exercise)

    suspend fun upsertAllExercises(exerciseList: List<ExerciseItem>) = db.getExerciseDao().upsertAll(exerciseList = exerciseList)

    suspend fun upsert(workout: Workout) = db.getExerciseDao().upsertWorkout(workout = workout)

    suspend fun getSavedExercises() = db.getExerciseDao().getAllExercises()

    suspend fun cacheExists(): Boolean = db.getExerciseDao().exists()

    suspend fun deleteAllExercises() = db.getExerciseDao().deleteAllExercises()

    suspend fun getAllWorkouts() = db.getExerciseDao().getAllWorkouts()

    suspend fun getLastWorkout() = db.getExerciseDao().getLastWorkout()

    suspend fun deleteAllWorkouts() = db.getExerciseDao().deleteAllWorkouts()

    suspend fun readBlocks()= db.getExerciseDao().readBlocks()

    suspend fun insertBlocks(data: Block) = db.getExerciseDao().insertBlocks(data = data)

    suspend fun getBlocksByID(id: Int) = db.getExerciseDao().getBlocksWithID(id)

    suspend fun getBlocksByExercise(exercise: ExerciseItem) = db.getExerciseDao().getBlocksWithExercises(exercise)

    suspend fun getWorkoutsByIDs(ids: List<Int>) = db.getExerciseDao().getWorkoutByIDs(ids)
}