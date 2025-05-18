package com.example.starlineapp.model

/**
 * Clase simplificada para JSON directa.
 * Esta clase proporciona múltiples formatos para serialización que podrían ser aceptados por el backend.
 */
class RutaJsonCreationRequest(
    val origenNombre: String,
    val destinoNombre: String,
    val billeteId: Long = 0
) {
    /**
     * Devuelve el JSON en formato plano, útil para depuración o para crear manualmente el RequestBody
     */
    fun toJsonString(): String {
        return """
            {
              "origenNombre": "$origenNombre",
              "destinoNombre": "$destinoNombre",
              "billeteId": $billeteId
            }
        """.trimIndent()
    }
    
    /**
     * Devuelve un formato alternativo con los datos envueltos en un objeto
     */
    fun toNestedJsonString(): String {
        return """
            {
              "request": {
                "origenNombre": "$origenNombre",
                "destinoNombre": "$destinoNombre",
                "billeteId": $billeteId
              }
            }
        """.trimIndent()
    }
    
    /**
     * Otro formato alternativo con estructura más compleja
     */
    fun toDetailedJsonString(): String {
        return """
            {
              "ruta": {
                "origen": {"nombre": "$origenNombre"},
                "destino": {"nombre": "$destinoNombre"}
              },
              "billeteId": $billeteId
            }
        """.trimIndent()
    }
} 