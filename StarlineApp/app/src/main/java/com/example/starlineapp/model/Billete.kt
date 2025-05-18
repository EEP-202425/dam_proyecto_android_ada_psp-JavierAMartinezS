package com.example.starlineapp.model

import com.google.gson.annotations.SerializedName

data class Billete(
    @SerializedName("id")
    val id: Long? = null,
    
    @SerializedName("rutaId")
    val rutaId: Long? = null
) 