package com.example.starlineapp.network

import com.example.starlineapp.model.SimpleParada
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ParadaService {
    @GET("paradas")
    suspend fun getParadasByRutaId(
        @Query("rutaId") rutaId: Long
    ): Response<List<SimpleParada>>
} 