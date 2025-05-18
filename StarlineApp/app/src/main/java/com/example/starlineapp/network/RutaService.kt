package com.example.starlineapp.network

import com.example.starlineapp.model.RutaRequest
import com.example.starlineapp.model.SimpleRutaAPI
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface RutaService {
    @GET("rutas")
    suspend fun getRutaByBilleteId(
        @Query("billeteId") billeteId: Long
    ): Response<SimpleRutaAPI>
    
    @GET("rutas")
    suspend fun getAllRutas(
        @Query("billeteId") billeteId: Long = 0
    ): Response<List<SimpleRutaAPI>>

    @POST("rutas")
    suspend fun createRutaSimpleQuery(
        @Query("origenNombre") origenNombre: String,
        @Query("destinoNombre") destinoNombre: String,
        @Query("billeteId") billeteId: Long = 0
    ): Response<Void>

    @POST("rutas")
    @Headers("Content-Type: application/json")
    suspend fun createRutaJson(
        @Body requestBody: RequestBody
    ): Response<Void>
    
    @DELETE("rutas/{id}")
    suspend fun deleteRuta(
        @Path("id") id: Long,
        @Query("billeteId") billeteId: Long = 0
    ): Response<Void>
} 