package com.example.move.db

import androidx.room.*
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

    @Query("DELETE FROM exercises")
    suspend fun deleteAll()

    @Query("SELECT EXISTS(SELECT * FROM exercises)")
    suspend fun exists(): Boolean

}