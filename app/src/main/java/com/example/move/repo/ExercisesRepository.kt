package com.example.move.repo

import com.example.move.data.remote.ExerciseAPI
import com.example.move.data.local.ExerciseDatabase
import com.example.move.models.Block
import com.example.move.models.ExerciseItem
import com.example.move.models.Workout
import com.example.move.models.WorkoutBlockCrossRef
import com.example.move.models.WorkoutWithBlocks
import javax.inject.Inject

class ExercisesRepository @Inject constructor(
    db: ExerciseDatabase,
    private val api: ExerciseAPI,
) {
    private val dao = db.dao

    // Exercises

    private suspend fun exercisesTableExists(): Boolean = dao.exists()

    suspend fun getExercisesFromApiOrDb(): List<ExerciseItem> {
        return if (!exercisesTableExists()) {
            val responseFromApi = getExercisesFromApi().body()!!
            insertExercises(responseFromApi)

            responseFromApi
        } else {
            getSavedExercises()
        }
    }

    private suspend fun getExercisesFromApi() = api.getAllExercises()

    private suspend fun insertExercises(exerciseList: List<ExerciseItem>) =
        dao.upsertAll(exerciseList = exerciseList)

    private suspend fun getSavedExercises() = dao.getAllExercises()

    // Workout

    suspend fun insertWorkout(workout: Workout) = dao.upsertWorkout(workout = workout)

    // CrossReference

    suspend fun insertCrossReference(ref: WorkoutBlockCrossRef) = dao.insertCrossReference(ref)

    suspend fun getWorkoutsWithBlocks(): List<WorkoutWithBlocks> = dao.getWorkoutsWithBlocks()

    // Blocks

    suspend fun insertBlocks(data: Block) = dao.insertBlock(data = data)

    suspend fun getBlocksByExercise(exercise: ExerciseItem) = dao.getBlocksWithExercises(exercise)

    // Misc

//    suspend fun deleteAllExercises() = dao.deleteAllExercises()
//
//    suspend fun getAllWorkouts() = dao.getAllWorkouts()
//
//    suspend fun getLastWorkout() = dao.getLastWorkout()
//
//    suspend fun deleteAllWorkouts() = dao.deleteAllWorkouts()
//
//    suspend fun getBlocksByID(id: Int) = dao.getBlocksWithID(id)
//
//    suspend fun getWorkoutsByIDs(ids: List<Int>) = dao.getWorkoutByIDs(ids)
}