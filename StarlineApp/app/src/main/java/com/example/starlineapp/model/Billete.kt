package com.example.starlineapp.model

import com.google.gson.annotations.SerializedName

data class Billete(
    @SerializedName("id")
    val id: Long? = null,
    
    // Puedes ampliar este modelo según la información que contenga el billete en tu API
    @SerializedName("rutaId")
    val rutaId: Long? = null
) 