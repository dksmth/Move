package com.example.move.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.move.models.ExerciseItem

@Dao
interface ExerciseDao {

    @Query("SELECT * FROM exercises")
    fun getAllExercises(): LiveData<List<ExerciseItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(exercise: ExerciseItem): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(exerciseList: List<ExerciseItem>)

    @Query("SELECT EXISTS(SELECT * FROM exercises)")
    fun isExists(): Boolean

}