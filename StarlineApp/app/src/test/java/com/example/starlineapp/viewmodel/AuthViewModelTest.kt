package com.example.starlineapp.viewmodel

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class AuthViewModelTest {
    
    private lateinit var authViewModel: AuthViewModel
    
    @Before
    fun configurar() {
        authViewModel = AuthViewModel()
    }
    
    @Test
    fun registroDeberiaCrearNuevoUsuarioCuandoLosDatosSonCorrectos() {
        val nombre = "Juan Pérez"
        val email = "juan@example.com"
        val password = "Password123"
        val resultado = authViewModel.register(nombre, email, password)
        assertTrue("El registro debería ser exitoso", resultado)
        val usuario = authViewModel.currentUser.value
        assertNotNull("El usuario actual no debería ser nulo", usuario)
        assertEquals("El nombre debe coincidir", nombre, usuario?.nombre)
        assertEquals("El email debe coincidir", email, usuario?.email)
    }
    
    @Test
    fun registroDeberiaFallarConEmailDuplicado() {
        authViewModel.register("Juan Pérez", "juan@example.com", "Password123")
        val resultadoSegundoRegistro = authViewModel.register("Otro Usuario", "juan@example.com", "OtraContraseña")
        assertFalse("El registro con email duplicado debería fallar", resultadoSegundoRegistro)
    }
    
    @Test
    fun loginDeberiaFuncionarConCredencialesCorrectas() {
        authViewModel.register("Juan Pérez", "juan@example.com", "Password123")
        authViewModel.logout()
        val resultadoLogin = authViewModel.login("juan@example.com", "Password123")
        assertTrue("El login debería ser exitoso", resultadoLogin)
        val usuario = authViewModel.currentUser.value
        assertNotNull("El usuario actual no debería ser nulo después del login", usuario)
        assertEquals("El email debe coincidir", "juan@example.com", usuario?.email)
    }
    
    @Test
    fun loginDeberiaFallarConCredencialesIncorrectas() {
        authViewModel.register("Juan Pérez", "juan@example.com", "Password123")
        authViewModel.logout()
        val resultadoEmailIncorrecto = authViewModel.login("noexiste@example.com", "Password123")
        assertFalse("Login con email incorrecto debería fallar", resultadoEmailIncorrecto)
        val resultadoContraseñaIncorrecta = authViewModel.login("juan@example.com", "ContraseñaIncorrecta")
    }
} 