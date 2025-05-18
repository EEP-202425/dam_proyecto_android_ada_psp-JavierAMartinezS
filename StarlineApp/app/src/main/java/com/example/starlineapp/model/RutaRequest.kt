package com.example.starlineapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RutaRequest(
    @Expose
    @SerializedName("origenNombre")
    val origenNombre: String,
    
    @Expose
    @SerializedName("destinoNombre")
    val destinoNombre: String,
    
    @Expose
    @SerializedName("billeteId")
    val billeteId: Long = 0
) 