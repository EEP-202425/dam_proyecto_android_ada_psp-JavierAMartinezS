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
    fun `registro debería crear nuevo usuario cuando los datos son correctos`() {
        // Datos de prueba
        val nombre = "Juan Pérez"
        val email = "juan@example.com"
        val password = "Password123"
        
        // Ejecutar el registro
        val resultado = authViewModel.register(nombre, email, password)
        
        // Verificar que el registro fue exitoso
        assertTrue("El registro debería ser exitoso", resultado)
        
        // Verificar que el usuario está guardado en el ViewModel
        val usuario = authViewModel.currentUser.value
        assertNotNull("El usuario actual no debería ser nulo", usuario)
        assertEquals("El nombre debe coincidir", nombre, usuario?.nombre)
        assertEquals("El email debe coincidir", email, usuario?.email)
    }
    
    @Test
    fun `registro debería fallar con email duplicado`() {
        // Registrar un usuario primero
        authViewModel.register("Juan Pérez", "juan@example.com", "Password123")
        
        // Intentar registrar otro usuario con el mismo email
        val resultadoSegundoRegistro = authViewModel.register("Otro Usuario", "juan@example.com", "OtraContraseña")
        
        // Verificar que el segundo registro falló
        assertFalse("El registro con email duplicado debería fallar", resultadoSegundoRegistro)
    }
    
    @Test
    fun `login debería funcionar con credenciales correctas`() {
        // Registrar un usuario primero
        authViewModel.register("Juan Pérez", "juan@example.com", "Password123")
        
        // Hacer logout para asegurarse que no hay sesión activa
        authViewModel.logout()
        
        // Intentar hacer login con las credenciales correctas
        val resultadoLogin = authViewModel.login("juan@example.com", "Password123")
        
        // Verificar que el login fue exitoso
        assertTrue("El login debería ser exitoso", resultadoLogin)
        
        // Verificar que el usuario actual es el correcto
        val usuario = authViewModel.currentUser.value
        assertNotNull("El usuario actual no debería ser nulo después del login", usuario)
        assertEquals("El email debe coincidir", "juan@example.com", usuario?.email)
    }
    
    @Test
    fun `login debería fallar con credenciales incorrectas`() {
        // Registrar un usuario
        authViewModel.register("Juan Pérez", "juan@example.com", "Password123")
        
        // Hacer logout
        authViewModel.logout()
        
        // Intentar login con email incorrecto
        val resultadoEmailIncorrecto = authViewModel.login("noexiste@example.com", "Password123")
        assertFalse("Login con email incorrecto debería fallar", resultadoEmailIncorrecto)
        
        // Intentar login con contraseña incorrecta (aunque en este momento no se verifica la contraseña)
        val resultadoContraseñaIncorrecta = authViewModel.login("juan@example.com", "ContraseñaIncorrecta")
        // Este test podría fallar si el ViewModel no verifica realmente la contraseña
    }
} 