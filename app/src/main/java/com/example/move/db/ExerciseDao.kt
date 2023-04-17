package com.example.move.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.move.models.Exercise
import com.example.move.models.ExerciseItem

@Dao
interface ExerciseDao {

    @Query("SELECT * FROM exercises")
    fun getAllExercises(): LiveData<List<ExerciseItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(exercise: ExerciseItem): Long

//    @Delete
//    suspend fun deleteArticle(exercise: ExerciseItem)

}