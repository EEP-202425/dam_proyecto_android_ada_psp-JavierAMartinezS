package com.example.starlineapp.repository

import android.util.Log
import com.example.starlineapp.model.Route
import com.example.starlineapp.model.RutaCreationRequest
import com.example.starlineapp.model.RutaJsonCreationRequest
import com.example.starlineapp.model.RutaRequest
import com.example.starlineapp.model.SimpleParada
import com.example.starlineapp.model.SimpleRutaAPI
import com.example.starlineapp.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class RutaRepository {
    private val TAG = "RutaRepository"
    private val rutaService = RetrofitClient.rutaService
    
    suspend fun getAllRutas(): Result<List<Route>> {
        return withContext(Dispatchers.IO) {
            try {
                // Siempre proporcionamos un billeteId por defecto (0)
                val response = rutaService.getAllRutas(billeteId = 0)
                if (response.isSuccessful) {
                    val routeList = response.body()?.map { it.toRoute() } ?: emptyList()
                    Result.success(routeList)
                } else {
                    Log.e(TAG, "Error al obtener rutas: ${response.code()} - ${response.message()}")
                    val errorBody = response.errorBody()?.string()
                    Log.e(TAG, "Cuerpo de error: $errorBody")
                    Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
                }
            } catch (e: Exception) {
                Log.e(TAG, "Excepción al obtener rutas: ${e.message}", e)
                Result.failure(e)
            }
        }
    }
    
    suspend fun getRutaByBilleteId(billeteId: Long): Result<Route?> {
        return withContext(Dispatchers.IO) {
            try {
                val response = rutaService.getRutaByBilleteId(billeteId)
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()?.toRoute())
                } else if (response.code() == 404) {
                    // No se encontró la ruta, pero no es un error
                    Result.success(null)
                } else {
                    Log.e(TAG, "Error al obtener ruta por billeteId: ${response.code()} - ${response.message()}")
                    val errorBody = response.errorBody()?.string()
                    Log.e(TAG, "Cuerpo de error: $errorBody")
                    Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
                }
            } catch (e: Exception) {
                Log.e(TAG, "Excepción al obtener ruta por billeteId: ${e.message}", e)
                Result.failure(e)
            }
        }
    }
    
    suspend fun createRuta(ruta: SimpleRutaAPI): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                Log.d(TAG, "Enviando datos para crear ruta - Origen: ${ruta.origen.nombre}, Destino: ${ruta.destino.nombre}")
                
                // Crear solicitud con múltiples formatos JSON
                val jsonRequest = RutaJsonCreationRequest(
                    origenNombre = ruta.origen.nombre,
                    destinoNombre = ruta.destino.nombre,
                    billeteId = 0
                )
                
                // Intentar múltiples formatos JSON para encontrar el que acepte el servidor
                val jsonResponse = tryAllJsonFormats(jsonRequest)
                
                // Si JSON falló, intentar con parámetros de consulta
                if (jsonResponse == null || !jsonResponse.isSuccessful) {
                    val errorDetails = jsonResponse?.let {
                        "${it.code()} - ${it.message()} - ${it.errorBody()?.string() ?: "Sin cuerpo"}"
                    } ?: "Sin respuesta JSON"
                    
                    Log.e(TAG, "Todos los formatos JSON fallaron: $errorDetails")
                    Log.d(TAG, "Intentando con parámetros de consulta...")
                    
                    val queryResponse = rutaService.createRutaSimpleQuery(
                        origenNombre = ruta.origen.nombre,
                        destinoNombre = ruta.destino.nombre,
                        billeteId = 0
                    )
                    
                    if (queryResponse.isSuccessful) {
                        Log.d(TAG, "Ruta creada exitosamente con parámetros de consulta: ${queryResponse.code()}")
                        return@withContext Result.success(true)
                    } else {
                        val queryErrorBody = queryResponse.errorBody()?.string() ?: "Sin cuerpo de error"
                        Log.e(TAG, "Error con parámetros de consulta: ${queryResponse.code()} - ${queryResponse.message()} - $queryErrorBody")
                        return@withContext Result.failure(Exception("Error: ${queryResponse.code()} - ${queryResponse.message()} - $queryErrorBody"))
                    }
                }
                
                // Si llegamos aquí, uno de los formatos JSON tuvo éxito
                Log.d(TAG, "Ruta creada exitosamente con JSON: ${jsonResponse.code()}")
                
                // Intenta obtener el ID generado por el servidor desde el header Location (si existe)
                val locationHeader = jsonResponse.headers().get("Location")
                if (locationHeader != null) {
                    try {
                        val idFromLocation = locationHeader.substringAfterLast("/").toLong()
                        Log.d(TAG, "Ruta creada con ID del servidor: $idFromLocation")
                    } catch (e: Exception) {
                        Log.e(TAG, "No se pudo extraer el ID del servidor: ${e.message}")
                    }
                }
                
                Result.success(true)
            } catch (e: Exception) {
                Log.e(TAG, "Excepción al crear ruta: ${e.message}")
                e.printStackTrace()
                Result.failure(e)
            }
        }
    }
    
    /**
     * Intenta todos los formatos JSON posibles hasta encontrar uno que funcione
     */
    private suspend fun tryAllJsonFormats(jsonRequest: RutaJsonCreationRequest): retrofit2.Response<Void>? {
        // Formato 1: JSON Básico
        try {
            val json = jsonRequest.toJsonString()
            Log.d(TAG, "Intentando formato JSON básico: $json")
            val requestBody = json.toRequestBody("application/json".toMediaType())
            val response = rutaService.createRutaJson(requestBody)
            if (response.isSuccessful) return response
            Log.e(TAG, "Formato básico falló: ${response.code()}")
        } catch (e: Exception) {
            Log.e(TAG, "Error con formato JSON básico: ${e.message}")
        }
        
        // Formato 2: JSON Anidado
        try {
            val json = jsonRequest.toNestedJsonString()
            Log.d(TAG, "Intentando formato JSON anidado: $json")
            val requestBody = json.toRequestBody("application/json".toMediaType())
            val response = rutaService.createRutaJson(requestBody)
            if (response.isSuccessful) return response
            Log.e(TAG, "Formato anidado falló: ${response.code()}")
        } catch (e: Exception) {
            Log.e(TAG, "Error con formato JSON anidado: ${e.message}")
        }
        
        // Formato 3: JSON Detallado
        try {
            val json = jsonRequest.toDetailedJsonString()
            Log.d(TAG, "Intentando formato JSON detallado: $json")
            val requestBody = json.toRequestBody("application/json".toMediaType())
            val response = rutaService.createRutaJson(requestBody)
            if (response.isSuccessful) return response
            Log.e(TAG, "Formato detallado falló: ${response.code()}")
        } catch (e: Exception) {
            Log.e(TAG, "Error con formato JSON detallado: ${e.message}")
        }
        
        // Formato 4: JSON Object Directo (como último recurso)
        try {
            val jsonObject = JSONObject().apply {
                put("origenNombre", jsonRequest.origenNombre)
                put("destinoNombre", jsonRequest.destinoNombre)
                put("billeteId", jsonRequest.billeteId)
            }
            Log.d(TAG, "Intentando con JSONObject directo: ${jsonObject}")
            val requestBody = jsonObject.toString().toRequestBody("application/json".toMediaType())
            return rutaService.createRutaJson(requestBody)
        } catch (e: Exception) {
            Log.e(TAG, "Error con JSONObject directo: ${e.message}")
        }
        
        return null
    }
    
    suspend fun deleteRuta(id: Long): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                // Aseguramos que se envía el parámetro billeteId (con valor por defecto)
                val response = rutaService.deleteRuta(id, billeteId = 0)
                if (response.isSuccessful) {
                    Result.success(true)
                } else {
                    Log.e(TAG, "Error al eliminar ruta: ${response.code()} - ${response.message()}")
                    val errorBody = response.errorBody()?.string()
                    Log.e(TAG, "Cuerpo de error: $errorBody")
                    Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
                }
            } catch (e: Exception) {
                Log.e(TAG, "Excepción al eliminar ruta: ${e.message}", e)
                Result.failure(e)
            }
        }
    }
    
    // Método para convertir un Route local a SimpleRutaAPI
    fun convertToRutaAPI(route: Route): SimpleRutaAPI {
        val origen = SimpleParada(
            nombre = route.origin,
            esOrigen = true,
            esDestino = false,
            esIntermedio = false
        )
        
        val destino = SimpleParada(
            nombre = route.destination,
            esOrigen = false,
            esDestino = true,
            esIntermedio = false
        )
        
        // Crear parada intermedia si existe descripción
        val paradas = mutableListOf(origen)
        if (route.description.isNotBlank() && !route.description.startsWith("Recorrido:")) {
            val intermedia = SimpleParada(
                nombre = route.description.take(20), // Limitar tamaño por si acaso
                esOrigen = false,
                esDestino = false,
                esIntermedio = true
            )
            paradas.add(intermedia)
        }
        paradas.add(destino)
        
        // Usar 0 como ID predeterminado para rutas nuevas o cuando el ID no es numérico
        val id = try {
            if (route.id.isBlank()) 0 else route.id.toLong()
        } catch (e: NumberFormatException) {
            // Si el ID es un UUID o no es numérico, usamos 0 para indicar una nueva ruta
            0
        }
        
        return SimpleRutaAPI(
            id = id,
            origen = origen,
            destino = destino,
            recorrido = paradas,
            transbordo = false,
            billete = null
        )
    }
} 