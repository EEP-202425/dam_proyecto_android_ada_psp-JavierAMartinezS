package com.example.proyectofinalandroid.data.repository

import com.example.proyectofinalandroid.data.network.StarlineApiService
import com.example.proyectofinalandroid.data.model.Ruta

class RutaRepository {
    private val api = StarlineApiService.rutaApi

    suspend fun getRutas(): List<Ruta> = api.getRutas()

    suspend fun addRuta(ruta: Ruta): Ruta = api.addRuta(ruta)

    suspend fun deleteRuta(id: Int) = api.deleteRuta(id)
} 