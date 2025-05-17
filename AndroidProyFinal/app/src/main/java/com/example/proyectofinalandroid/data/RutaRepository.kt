package com.example.proyectofinalandroid.data

import com.example.proyectofinalandroid.data.network.StarlineApiService
import com.example.proyectofinalandroid.data.model.Ruta

class RutaRepository {
    private val api = StarlineApiService.rutaApi

    suspend fun obtenerElementos(): List<Ruta> {
        return api.getRutas().toList()
    }

    suspend fun agregarElemento(elemento: Elemento) {
        api.agregarElemento(elemento)
    }
} 