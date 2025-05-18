package com.example.starlineapp.model

import com.google.gson.annotations.SerializedName

data class Linea(
    @SerializedName("id")
    val id: Long,
    
    @SerializedName("nombre")
    val nombre: String,
    
    @SerializedName("trenesCorrespondientes")
    val trenesCorrespondientes: List<Tren>,
    
    @SerializedName("paradas")
    val paradas: List<Parada>,
    
    @SerializedName("rutas")
    val rutas: List<RutaAPI>
) 