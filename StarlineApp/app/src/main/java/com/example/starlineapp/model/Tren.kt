package com.example.starlineapp.model

import com.google.gson.annotations.SerializedName

data class Tren(
    @SerializedName("id")
    val id: Long,
    
    @SerializedName("rutaCorrespondiente")
    val rutaCorrespondiente: RutaAPI
) 