package com.example.starlineapp.model

import com.example.starlineapp.R

data class Route(
    val id: String,
    val origin: String,
    val destination: String,
    val description: String = "",
    val imageResId: Int = R.drawable.tren_starline // Imagen por defecto
) 