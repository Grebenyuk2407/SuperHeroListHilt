package com.example.superherolisthilt

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import javax.inject.Inject



interface ApiInterface {
    @GET("/superhero-api/api/all.json")
    suspend fun getSuperHeroes(): SuperHero
}

