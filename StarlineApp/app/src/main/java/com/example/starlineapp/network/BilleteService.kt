package com.example.starlineapp.network

import com.example.starlineapp.model.SimpleBillete
import retrofit2.Response
import retrofit2.http.*

interface BilleteService {
    @GET("billetes")
    suspend fun getBilletes(): Response<List<SimpleBillete>>
    
    @POST("billetes")
    suspend fun createBillete(
        @Body billete: SimpleBillete
    ): Response<Void>
    
    @DELETE("billetes/{id}")
    suspend fun deleteBillete(
        @Path("id") id: Long
    ): Response<Void>
} 