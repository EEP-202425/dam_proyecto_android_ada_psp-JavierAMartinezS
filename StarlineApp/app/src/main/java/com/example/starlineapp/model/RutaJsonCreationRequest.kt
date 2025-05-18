package com.example.starlineapp.model

class RutaJsonCreationRequest(
    val origenNombre: String,
    val destinoNombre: String,
    val billeteId: Long = 0
) {
    fun toJsonString(): String {
        return """
            {
              "origenNombre": "$origenNombre",
              "destinoNombre": "$destinoNombre",
              "billeteId": $billeteId
            }
        """.trimIndent()
    }

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