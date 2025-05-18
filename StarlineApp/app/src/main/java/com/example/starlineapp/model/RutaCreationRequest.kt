package com.example.starlineapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

// Clase mínima para crear rutas
data class RutaCreationRequest(
    @Expose
    @SerializedName("origen")
    val origen: ParadaMinimal,
    
    @Expose
    @SerializedName("destino")
    val destino: ParadaMinimal,
    
    @Expose
    @SerializedName("transbordo")
    val transbordo: Boolean = false
) {
    data class ParadaMinimal(
        @Expose
        @SerializedName("nombre")
        val nombre: String
    )
    
    companion object {
        fun fromSimpleRutaAPI(rutaAPI: SimpleRutaAPI): RutaCreationRequest {
            return RutaCreationRequest(
                origen = ParadaMinimal(nombre = rutaAPI.origen.nombre),
                destino = ParadaMinimal(nombre = rutaAPI.destino.nombre),
                transbordo = rutaAPI.transbordo
            )
        }
    }
} 