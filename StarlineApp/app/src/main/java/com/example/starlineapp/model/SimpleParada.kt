package com.example.starlineapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SimpleParada(
    @Expose
    @SerializedName("nombre")
    val nombre: String,
    
    @Expose
    @SerializedName("esOrigen")
    val esOrigen: Boolean,
    
    @Expose
    @SerializedName("esDestino")
    val esDestino: Boolean,
    
    @Expose
    @SerializedName("esIntermedio")
    val esIntermedio: Boolean
) 