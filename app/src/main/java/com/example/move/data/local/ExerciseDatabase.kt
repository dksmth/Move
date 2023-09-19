package com.example.move.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.move.models.Block
import com.example.move.models.ExerciseItem
import com.example.move.models.Workout
import com.example.move.models.WorkoutBlockCrossRef

@Database(
    entities = [ExerciseItem::class, Workout::class, Block::class, WorkoutBlockCrossRef::class],
    version = 16,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class ExerciseDatabase: RoomDatabase()  {

    abstract val dao: ExerciseDao
}