package com.example.starlineapp.model

import com.google.gson.annotations.SerializedName

data class RutaAPI(
    @SerializedName("id")
    val id: Long,
    
    @SerializedName("origen")
    val origen: Parada,
    
    @SerializedName("destino")
    val destino: Parada,
    
    @SerializedName("recorrido")
    val recorrido: List<Parada>,
    
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
            "id" to id,
            "origen" to mapOf(
                "nombre" to origen.nombre,
                "esOrigen" to origen.esOrigen,
                "esDestino" to origen.esDestino,
                "esIntermedio" to origen.esIntermedio
            ),
            "destino" to mapOf(
                "nombre" to destino.nombre,
                "esOrigen" to destino.esOrigen,
                "esDestino" to destino.esDestino,
                "esIntermedio" to destino.esIntermedio
            ),
            "recorrido" to recorrido.map { parada ->
                mapOf(
                    "nombre" to parada.nombre,
                    "esOrigen" to parada.esOrigen,
                    "esDestino" to parada.esDestino,
                    "esIntermedio" to parada.esIntermedio
                )
            },
            "transbordo" to transbordo,
            "billete" to null
        )
    }
} 