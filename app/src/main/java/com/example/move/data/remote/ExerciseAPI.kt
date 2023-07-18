package com.example.move.data.remote

import com.example.move.models.ExerciseItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface ExerciseAPI {

    @Headers(
        "X-RapidAPI-Key: 32789e5e20msh3727700efc75f68p17a5e4jsna4bb7b0277c3",
        "X-RapidAPI-Host: exercisedb.p.rapidapi.com"
    )
    @GET("/exercises")
    suspend fun getAllExercises(): Response<List<ExerciseItem>>

}