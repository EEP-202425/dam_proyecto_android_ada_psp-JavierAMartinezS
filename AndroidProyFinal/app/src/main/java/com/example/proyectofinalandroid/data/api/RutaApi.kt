package com.example.proyectofinalandroid.data.api

import com.example.proyectofinalandroid.data.model.Ruta
import retrofit2.http.*

interface RutaApi {
    @GET("rutas")
    suspend fun getRutas(): List<Ruta>

    @POST("rutas")
    suspend fun addRuta(@Body ruta: Ruta): Ruta

    @DELETE("rutas/{id}")
    suspend fun deleteRuta(@Path("id") id: Int)
} 