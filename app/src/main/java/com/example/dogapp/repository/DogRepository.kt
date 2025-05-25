package com.example.dogapp.repository

import com.example.dogapp.model.DogImage
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface DogApi {
    @GET("breeds/image/random")
    suspend fun getRandomDogImage(): DogImage
}

class DogRepository {
    private val api: DogApi = Retrofit.Builder()
        .baseUrl("https://dog.ceo/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(DogApi::class.java)

    suspend fun getRandomDogImage(): DogImage {
        return try {
            api.getRandomDogImage()
        } catch (e: Exception) {
            throw Exception("Failed to load dog image")
        }
    }
} 