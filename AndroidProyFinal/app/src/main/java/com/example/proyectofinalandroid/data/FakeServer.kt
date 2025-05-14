package com.example.proyectofinalandroid.data

import kotlinx.coroutines.delay

object FakeServer {
    private val elementos = mutableListOf(
        Elemento("Elemento 1"),
        Elemento("Elemento 2")
    )

    suspend fun obtenerElementos(): List<Elemento> {
        delay(1000)
        return elementos.toList()
    }

    suspend fun agregarElemento(e: Elemento) {
        delay(500)
        elementos.add(e)
    }
}