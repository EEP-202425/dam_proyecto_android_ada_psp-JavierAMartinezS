package com.example.starlineapp.repository

import com.example.starlineapp.model.Route
import com.example.starlineapp.model.SimpleParada
import com.example.starlineapp.model.SimpleRutaAPI
import com.example.starlineapp.network.RetrofitClient
import com.example.starlineapp.network.RutaService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.ArgumentMatchers.anyString
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import retrofit2.Response
import retrofit2.mock.Calls
import java.io.IOException

@ExperimentalCoroutinesApi
class RutaRepositoryTest {
    
    private lateinit var repositorio: RutaRepository
    private lateinit var servicioRutaMock: RutaService
    
    @Before
    fun configurar() {
        // Crear mock del servicio
        servicioRutaMock = mock(RutaService::class.java)
        
        // Configurar la instancia del repositorio para usar el servicio mock
        repositorio = RutaRepository().apply {
            // Aquí estamos utilizando reflection para establecer el servicio mock
            // en lugar del servicio real que se obtiene con RetrofitClient
            javaClass.getDeclaredField("rutaService").apply {
                isAccessible = true
                set(this@apply, servicioRutaMock)
            }
        }
    }
    
    @Test
    fun `crearRuta debería retornar éxito cuando la llamada al servicio es exitosa`() = runTest {
        // Preparar datos de prueba
        val origen = SimpleParada("Madrid", true, false, false)
        val destino = SimpleParada("Barcelona", false, true, false)
        val recorrido = listOf(origen, destino)
        val ruta = SimpleRutaAPI(1L, origen, destino, recorrido, false, null)
        
        // Configurar el comportamiento del mock para el método createRutaJson
        `when`(servicioRutaMock.createRutaJson(any())).thenReturn(
            Response.success(null)
        )
        
        // Ejecutar el método a probar
        val resultado = repositorio.createRuta(ruta)
        
        // Verificar el resultado
        assertTrue(resultado.isSuccess)
        assertTrue(resultado.getOrNull() == true)
    }
    
    @Test
    fun `crearRuta debería usar parámetros de consulta cuando JSON falla`() = runTest {
        // Preparar datos de prueba
        val origen = SimpleParada("Madrid", true, false, false)
        val destino = SimpleParada("Barcelona", false, true, false)
        val recorrido = listOf(origen, destino)
        val ruta = SimpleRutaAPI(1L, origen, destino, recorrido, false, null)
        
        // Configurar el comportamiento del mock para el método createRutaJson (falla)
        `when`(servicioRutaMock.createRutaJson(any())).thenReturn(
            Response.error(
                415, 
                "Tipo de medio no soportado".toResponseBody("text/plain".toMediaTypeOrNull())
            )
        )
        
        // Configurar el comportamiento del mock para el método createRutaSimpleQuery (éxito)
        `when`(servicioRutaMock.createRutaSimpleQuery(
            anyString(), anyString(), anyLong()
        )).thenReturn(
            Response.success(null)
        )
        
        // Ejecutar el método a probar
        val resultado = repositorio.createRuta(ruta)
        
        // Verificar el resultado
        assertTrue(resultado.isSuccess)
        assertTrue(resultado.getOrNull() == true)
    }
    
    @Test
    fun `crearRuta debería retornar fallo cuando todos los métodos fallan`() = runTest {
        // Preparar datos de prueba
        val origen = SimpleParada("Madrid", true, false, false)
        val destino = SimpleParada("Barcelona", false, true, false)
        val recorrido = listOf(origen, destino)
        val ruta = SimpleRutaAPI(1L, origen, destino, recorrido, false, null)
        
        // Configurar el comportamiento del mock para ambos métodos (fallan)
        `when`(servicioRutaMock.createRutaJson(any())).thenReturn(
            Response.error(
                415, 
                "Tipo de medio no soportado".toResponseBody("text/plain".toMediaTypeOrNull())
            )
        )
        
        `when`(servicioRutaMock.createRutaSimpleQuery(
            anyString(), anyString(), anyLong()
        )).thenReturn(
            Response.error(
                400, 
                "Solicitud incorrecta".toResponseBody("text/plain".toMediaTypeOrNull())
            )
        )
        
        // Ejecutar el método a probar
        val resultado = repositorio.createRuta(ruta)
        
        // Verificar el resultado
        assertTrue(resultado.isFailure)
    }
    
    @Test
    fun `eliminarRuta debería retornar éxito cuando la llamada al servicio es exitosa`() = runTest {
        // Configurar el comportamiento del mock
        `when`(servicioRutaMock.deleteRuta(anyLong(), anyLong())).thenReturn(
            Response.success(null)
        )
        
        // Ejecutar el método a probar
        val resultado = repositorio.deleteRuta(1L)
        
        // Verificar el resultado
        assertTrue(resultado.isSuccess)
        assertTrue(resultado.getOrNull() == true)
    }
    
    @Test
    fun `eliminarRuta debería retornar fallo cuando la llamada al servicio falla`() = runTest {
        // Configurar el comportamiento del mock
        `when`(servicioRutaMock.deleteRuta(anyLong(), anyLong())).thenReturn(
            Response.error(
                404, 
                "No encontrado".toResponseBody("text/plain".toMediaTypeOrNull())
            )
        )
        
        // Ejecutar el método a probar
        val resultado = repositorio.deleteRuta(1L)
        
        // Verificar el resultado
        assertTrue(resultado.isFailure)
    }
    
    @Test
    fun `convertirARutaAPI debería convertir correctamente Route a SimpleRutaAPI`() {
        // Preparar datos de prueba
        val ruta = Route(
            id = "1",
            origin = "Madrid",
            destination = "Barcelona",
            description = "Vía Valencia"
        )
        
        // Ejecutar el método a probar
        val resultado = repositorio.convertToRutaAPI(ruta)
        
        // Verificar el resultado
        assertEquals(1L, resultado.id)
        assertEquals("Madrid", resultado.origen.nombre)
        assertEquals("Barcelona", resultado.destino.nombre)
        assertEquals(3, resultado.recorrido.size) // origen + intermedia + destino
        assertTrue(resultado.recorrido[0].esOrigen)
        assertTrue(resultado.recorrido[1].esIntermedio)
        assertTrue(resultado.recorrido[2].esDestino)
    }
} 