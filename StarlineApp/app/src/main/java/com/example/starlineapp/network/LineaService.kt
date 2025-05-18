package com.example.starlineapp.network

import com.example.starlineapp.model.SimpleLinea
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LineaService {
    @GET("lineas")
    suspend fun getLineas(
        @Query("origenId") origenId: Long? = null,
        @Query("destinoId") destinoId: Long? = null
    ): Response<List<SimpleLinea>>
} 