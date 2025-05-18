package com.example.starlineapp.model

import com.example.starlineapp.R

data class Ruta(
    val id: String,
    val origin: String,
    val destination: String,
    val description: String = "",
    val imageResId: Int = R.drawable.tren_starline
) 