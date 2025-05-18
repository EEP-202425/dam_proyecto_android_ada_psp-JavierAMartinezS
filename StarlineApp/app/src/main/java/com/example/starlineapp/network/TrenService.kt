package com.example.starlineapp.network

import com.example.starlineapp.model.SimpleTren
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TrenService {
    @GET("trenes")
    suspend fun getTrenes(
        @Query("origenId") origenId: Long? = null,
        @Query("destinoId") destinoId: Long? = null
    ): Response<List<SimpleTren>>
} 