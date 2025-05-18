package com.example.starlineapp.model

import com.google.gson.annotations.SerializedName

data class Parada(
    @SerializedName("nombre")
    val nombre: String,
    
    @SerializedName("esOrigen")
    val esOrigen: Boolean,
    
    @SerializedName("esDestino")
    val esDestino: Boolean,
    
    @SerializedName("esIntermedio")
    val esIntermedio: Boolean
) 