package com.example.starlineapp.model

data class SimpleLinea(
    val id: Long,
    val nombre: String,
    val trenesCorrespondientes: List<SimpleTren> = emptyList(),
    val paradas: List<SimpleParada> = emptyList(),
    val rutas: List<SimpleRutaAPI> = emptyList()
) 