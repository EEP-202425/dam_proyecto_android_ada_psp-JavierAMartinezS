package com.example.starlineapp.repository

import com.example.starlineapp.model.SimpleParada
import com.example.starlineapp.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ParadaRepository {
    private val paradaService = RetrofitClient.paradaService
    
    suspend fun getParadasByRutaId(rutaId: Long): Result<List<SimpleParada>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = paradaService.getParadasByRutaId(rutaId)
                if (response.isSuccessful) {
                    Result.success(response.body() ?: emptyList())
                } else {
                    Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
} 