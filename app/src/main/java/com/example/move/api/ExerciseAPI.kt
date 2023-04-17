package com.example.move.api

import com.example.move.models.Exercise
import com.example.move.models.ExerciseItem
import com.example.move.util.Constants.Companion.API_HOST
import com.example.move.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface ExerciseAPI {

    @Headers(
        "X-RapidAPI-Key: 32789e5e20msh3727700efc75f68p17a5e4jsna4bb7b0277c3",
        "X-RapidAPI-Host: exercisedb.p.rapidapi.com"
    )
    @GET("/exercises")
    suspend fun getAllExercises(): Response<List<ExerciseItem>>

}