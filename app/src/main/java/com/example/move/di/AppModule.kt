package com.example.move.di

import android.app.Application
import androidx.room.Room
import com.example.move.data.remote.ExerciseAPI
import com.example.move.data.local.ExerciseDatabase
import com.example.move.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideStockApi(): ExerciseAPI {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideStockDatabase(app: Application): ExerciseDatabase {
        return Room.databaseBuilder(
            app,
            ExerciseDatabase::class.java,
            "exercise_db.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

}