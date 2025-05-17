package com.example.proyectofinalandroid.data.api

import com.example.proyectofinalandroid.data.Elemento
import com.example.proyectofinalandroid.data.model.Ruta
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RutaApi {
    @GET("rutas")
    suspend fun getRutas(): List<Ruta>

    @POST("rutas")
    suspend fun agregarElemento(@Body elemento: Elemento)

    @DELETE("rutas/{id}")
    suspend fun deleteRuta(@Path("id") id: Int)
} 