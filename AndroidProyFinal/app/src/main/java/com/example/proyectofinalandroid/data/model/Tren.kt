package com.example.proyectofinalandroid.data.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Tren(@SerializedName("id")
                val id: Long,)
