package com.example.starlineapp.model

import com.example.starlineapp.model.Route
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SimpleRutaAPI(
    @Expose
    @SerializedName("id")
    val id: Long,
    
    @Expose
    @SerializedName("origen")
    val origen: SimpleParada,
    
    @Expose
    @SerializedName("destino")
    val destino: SimpleParada,
    
    @Expose
    @SerializedName("recorrido")
    val recorrido: List<SimpleParada>,
    
    @Expose
    @SerializedName("transbordo")
    val transbordo: Boolean,
    
    @SerializedName("billete")
    val billete: Any? = null
) {
    // Método para convertir el modelo simple a modelo local
    fun toRoute(): Route {
        return Route(
            id = id.toString(),
            origin = origen.nombre,
            destination = destino.nombre,
            description = "Recorrido: ${recorrido.joinToString(" → ") { it.nombre }}"
        )
    }
    
    // Crear una versión minimalista para enviar al servidor
    fun toMinimal(): Map<String, Any?> {
        // Versión simplificada al máximo para evitar problemas
        return mapOf(
            "origen" to mapOf(
                "nombre" to origen.nombre
            ),
            "destino" to mapOf(
                "nombre" to destino.nombre
            ),
            "transbordo" to false
        )
    }
} 