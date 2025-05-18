package com.example.starlineapp.network

import android.util.Log
import com.google.gson.GsonBuilder
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val TAG = "RetrofitClient"
    private const val BASE_URL = "http://10.0.2.2:8085/"
    
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    
    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor { chain ->
            val original = chain.request()
            val builder = original.newBuilder()
            if (original.header("Content-Type") == null) {
                builder.header("Content-Type", "application/json")
            }

            builder.header("Accept", "application/json")
            
            val newRequest = builder
                .method(original.method, original.body)
                .build()

            Log.d(TAG, "Enviando solicitud a: ${newRequest.url}")
            Log.d(TAG, "Método: ${newRequest.method}")
            Log.d(TAG, "Headers: ${newRequest.headers}")
            if (original.body != null) {
                Log.d(TAG, "Content-Type del body: ${original.body?.contentType()}")
            }
            
            val response = chain.proceed(newRequest)
            Log.d(TAG, "Respuesta del servidor: ${response.code}")
            if (!response.isSuccessful) {
                val errorBody = response.peekBody(4096).string()
                Log.e(TAG, "Error del servidor: $errorBody")
            }
            
            response
        }
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()
    private val gson = GsonBuilder()
        .serializeNulls()
        .setLenient()
        .disableHtmlEscaping()
        .excludeFieldsWithoutExposeAnnotation()
        .create()
    
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val rutaService: RutaService by lazy {
        retrofit.create(RutaService::class.java)
    }
    
    val paradaService: ParadaService by lazy {
        retrofit.create(ParadaService::class.java)
    }
    
    val lineaService: LineaService by lazy {
        retrofit.create(LineaService::class.java)
    }
    
    val trenService: TrenService by lazy {
        retrofit.create(TrenService::class.java)
    }
    
    val billeteService: BilleteService by lazy {
        retrofit.create(BilleteService::class.java)
    }
} 