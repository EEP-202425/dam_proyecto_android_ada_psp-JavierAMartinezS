package com.example.proyectofinalandroid.data.network

import com.example.proyectofinalandroid.data.model.Linea
import com.example.proyectofinalandroid.data.model.Parada
import com.example.proyectofinalandroid.data.model.Ruta
import com.example.proyectofinalandroid.data.model.Tren
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path


private const val BASE_URL = "http://localhost:8085/" // Por ejemplo: "http://192.168.1.100:8080/"


private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

interface StarlineApiService {

    @GET("rutas?billeteId=")
    suspend fun getRutas(@Path(value = "id") id: Long): List<Ruta>

    @GET("paradas?rutaId=")
    suspend fun getParadas(@Path(value = "id") id: Long): List<Parada>

    @GET("lineas?origenId=&destinoId=")
    suspend fun getLineas(@Path(value = "id") id: Long): List<Linea>
    
    @GET("trenes?origenId=&destinoId=")
    suspend fun getTrenes(@Path(value = "id") id: Long): List<Tren>
}
object StarlineApi {
    val retrofitService: StarlineApiService by lazy {
        retrofit.create(StarlineApiService::class.java) //El get photos tirara con la URL del retrofit(.baseURL)

    }
}