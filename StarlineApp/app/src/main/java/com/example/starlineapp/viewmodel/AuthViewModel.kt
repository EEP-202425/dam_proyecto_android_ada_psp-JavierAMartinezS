package com.example.starlineapp.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class User(
    val id: String,
    val nombre: String,
    val email: String
)

class AuthViewModel : ViewModel() {
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    private val users = mutableListOf<User>()

    fun register(nombre: String, email: String, password: String): Boolean {
        // En una aplicación real, aquí se haría la llamada a una API o base de datos
        if (users.any { it.email == email }) {
            return false
        }

        val newUser = User(
            id = java.util.UUID.randomUUID().toString(),
            nombre = nombre,
            email = email
        )
        users.add(newUser)
        _currentUser.value = newUser
        return true
    }

    fun login(email: String, password: String): Boolean {
        // En una aplicación real, aquí se verificarían las credenciales contra una API o base de datos
        val user = users.find { it.email == email }
        if (user != null) {
            _currentUser.value = user
            return true
        }
        return false
    }

    fun logout() {
        _currentUser.value = null
    }
} 