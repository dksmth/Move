package com.example.move.db

import androidx.room.*
import com.example.move.models.Block
import com.example.move.models.ExerciseItem
import com.example.move.models.Workout

@Dao
interface ExerciseDao {

    @Query("SELECT * FROM exercises")
    suspend fun getAllExercises(): List<ExerciseItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(exercise: ExerciseItem): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertWorkout(workout: Workout): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(exerciseList: List<ExerciseItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBlocks(data: Block): Long

    @Query("DELETE FROM exercises")
    suspend fun deleteAllExercises()

    @Query("SELECT EXISTS(SELECT * FROM exercises)")
    suspend fun exists(): Boolean

    @Query("SELECT * FROM workouts")
    suspend fun getAllWorkouts(): List<Workout>

    @Query("SELECT * FROM workouts ORDER BY workout_id DESC LIMIT 1")
    suspend fun getLastWorkout(): Workout

    @Query("DELETE FROM workouts")
    suspend fun deleteAllWorkouts()

    @Query("SELECT * FROM workouts JOIN blocks ON workouts.workout_id = blocks.workout_id")
    suspend fun readBlocks(): Map<Workout, List<Block>>

    @Query("SELECT * FROM blocks where block_id = :block_id")
    suspend fun getBlocksWithID(block_id: Int): List<Block>

    @Query("SELECT * FROM blocks where exercise = :exercise")
    suspend fun getBlocksWithExercises(exercise: ExerciseItem): List<Block>
}