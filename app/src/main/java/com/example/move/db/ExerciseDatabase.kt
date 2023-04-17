package com.example.move.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.move.models.ExerciseItem

@Database(
    entities = [ExerciseItem::class],
    version = 1
)
abstract class ExerciseDatabase: RoomDatabase()  {

    abstract fun getExerciseDao(): ExerciseDao

    companion object {
        @Volatile
        private var instance: ExerciseDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ExerciseDatabase::class.java,
                "exercise_db.db"
            ).build()
    }
}