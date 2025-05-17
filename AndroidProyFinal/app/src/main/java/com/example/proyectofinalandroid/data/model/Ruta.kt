package com.example.proyectofinalandroid.data.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Ruta(
    @SerializedName("id")
    val id: Long,
    
    @SerializedName("origen")
    val origen: String,
    
    @SerializedName("destino")
    val destino: String
) 