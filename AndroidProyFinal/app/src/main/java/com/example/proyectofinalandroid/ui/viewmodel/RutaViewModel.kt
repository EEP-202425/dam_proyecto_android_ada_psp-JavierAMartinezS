package com.example.proyectofinalandroid.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectofinalandroid.data.model.Ruta
import com.example.proyectofinalandroid.data.repository.RutaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RutaViewModel : ViewModel() {
    private val repository = RutaRepository()
    private val _rutas = MutableStateFlow<List<Ruta>>(emptyList())
    val rutas: StateFlow<List<Ruta>> = _rutas

    init {
        cargarRutas()
    }

    private fun cargarRutas() {
        viewModelScope.launch {
            try {
                _rutas.value = repository.getRutas()
            } catch (e: Exception) {
                // Manejar el error
            }
        }
    }

    fun addRuta(origen: String, destino: String) {
        viewModelScope.launch {
            try {
                val nuevaRuta = Ruta(0, origen, destino) // El ID será asignado por el servidor
                repository.addRuta(nuevaRuta)
                cargarRutas()
            } catch (e: Exception) {
                // Manejar el error
            }
        }
    }

    fun deleteRuta(id: Int) {
        viewModelScope.launch {
            try {
                repository.deleteRuta(id)
                cargarRutas()
            } catch (e: Exception) {
                // Manejar el error
            }
        }
    }
} 