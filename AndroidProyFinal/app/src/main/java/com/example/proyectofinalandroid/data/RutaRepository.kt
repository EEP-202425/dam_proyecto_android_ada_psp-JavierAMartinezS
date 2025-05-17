package com.example.proyectofinalandroid.data

import com.example.proyectofinalandroid.data.api.RetrofitClient
import com.example.proyectofinalandroid.data.model.Ruta

class RutaRepository {
    private val api = RetrofitClient.rutaApi

    suspend fun obtenerElementos(): List<Ruta> {
        return api.getRutas().toList()
    }

    suspend fun agregarElemento(elemento: Elemento) {
        api.agregarElemento(elemento)
    }
} 