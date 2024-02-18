package com.example.superherolisthilt

import retrofit2.http.GET


interface ApiInterface {
    @GET("/superhero-api/api/all.json")
    suspend fun getSuperHeroes(): List<SuperHeroItem>
}

