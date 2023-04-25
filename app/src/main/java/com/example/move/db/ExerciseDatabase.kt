package com.example.move.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.move.models.ExerciseItem
import com.example.move.models.Workout

@Database(
    entities = [ExerciseItem::class, Workout::class],
    version = 2
)
@TypeConverters(Converters::class)
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
            )
                .fallbackToDestructiveMigration()
                .build()
    }
}