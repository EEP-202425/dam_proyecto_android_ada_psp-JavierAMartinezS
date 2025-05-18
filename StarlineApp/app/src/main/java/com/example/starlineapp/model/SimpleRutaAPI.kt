package com.example.starlineapp.model

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
    fun toRoute(): Ruta {
        return Ruta(
            id = id.toString(),
            origin = origen.nombre,
            destination = destino.nombre,
            description = "Recorrido: ${recorrido.joinToString(" → ") { it.nombre }}"
        )
    }

    fun toMinimal(): Map<String, Any?> {
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