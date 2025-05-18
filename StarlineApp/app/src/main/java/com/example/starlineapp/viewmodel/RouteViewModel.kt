package com.example.starlineapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starlineapp.R
import com.example.starlineapp.model.Route
import com.example.starlineapp.repository.RutaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class RouteViewModel : ViewModel() {
    private val _routes = MutableStateFlow<List<Route>>(emptyList())
    val routes: StateFlow<List<Route>> = _routes.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()
    
    private val rutaRepository = RutaRepository()
    
    init {
        loadRoutes()
    }
    
    fun loadRoutes() {
        _isLoading.value = true
        _error.value = null
        
        viewModelScope.launch {
            rutaRepository.getAllRutas().fold(
                onSuccess = { routesList ->
                    _routes.value = routesList
                },
                onFailure = { throwable ->
                    _error.value = "Error al cargar rutas: ${throwable.message}"
                }
            )
            _isLoading.value = false
        }
    }

    fun addRoute(origin: String, destination: String, description: String = "") {
        if (origin.isBlank() || destination.isBlank()) {
            _error.value = "Origen y destino son obligatorios"
            return
        }
        
        _isLoading.value = true
        _error.value = null
        
        // Primero agregamos la ruta localmente para mejor UX
        val newRoute = Route(
            id = UUID.randomUUID().toString(),
            origin = origin,
            destination = destination,
            description = description,
            imageResId = R.drawable.tren_starline
        )
        
        // Actualizamos primero la UI
        _routes.value = _routes.value + newRoute
        
        viewModelScope.launch {
            try {
                val rutaAPI = rutaRepository.convertToRutaAPI(newRoute)
                
                rutaRepository.createRuta(rutaAPI).fold(
                    onSuccess = { success ->
                        if (success) {
                            // Recargar todas las rutas para obtener la información actualizada del servidor
                            loadRoutes()
                        } else {
                            _error.value = "No se pudo crear la ruta en el servidor"
                        }
                    },
                    onFailure = { throwable ->
                        _error.value = "Error al crear ruta: ${throwable.message}"
                        // No revertimos la UI para no confundir al usuario
                    }
                )
            } catch (e: Exception) {
                _error.value = "Error inesperado: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteRoute(id: String) {
        _isLoading.value = true
        _error.value = null
        
        viewModelScope.launch {
            // Primero actualizamos localmente
            _routes.value = _routes.value.filter { it.id != id }
            
            // Luego intentamos eliminar en el servidor si el ID es numérico
            try {
                val idLong = id.toLong()
                rutaRepository.deleteRuta(idLong).fold(
                    onSuccess = { 
                        // Recargar todas las rutas para mantener sincronización
                        loadRoutes()
                    },
                    onFailure = { throwable ->
                        _error.value = "Error al eliminar en el servidor: ${throwable.message}"
                    }
                )
            } catch (e: NumberFormatException) {
                // Si el ID no es numérico, no intentamos eliminarlo del servidor
                // porque probablemente sea un ID local que no existe en el backend
            }
            
            _isLoading.value = false
        }
    }

    fun getRoute(id: String): Route? {
        return _routes.value.find { it.id == id }
    }

    fun updateRoute(id: String, origin: String, destination: String, description: String) {
        if (origin.isBlank() || destination.isBlank()) {
            _error.value = "Origen y destino son obligatorios"
            return
        }
        
        _isLoading.value = true
        _error.value = null
        
        val updatedRoute = Route(
            id = id,
            origin = origin,
            destination = destination,
            description = description,
            imageResId = R.drawable.tren_starline
        )
        
        // Actualizamos localmente primero
        updateLocalRoute(id, origin, destination, description)
        
        viewModelScope.launch {
            try {
                // Luego intentamos actualizar en el servidor si el ID es numérico
                try {
                    val idLong = id.toLong()
                    val deleteResult = rutaRepository.deleteRuta(idLong)
                    
                    if (deleteResult.isSuccess) {
                        val rutaAPI = rutaRepository.convertToRutaAPI(updatedRoute)
                        rutaRepository.createRuta(rutaAPI).fold(
                            onSuccess = { 
                                // Recargar todas las rutas para obtener la información actualizada
                                loadRoutes()
                            },
                            onFailure = { throwable ->
                                _error.value = "Error al actualizar en el servidor: ${throwable.message}"
                            }
                        )
                    } else {
                        _error.value = "No se pudo eliminar la ruta antigua del servidor"
                    }
                } catch (e: NumberFormatException) {
                    // Si el ID no es numérico, no intentamos actualizarlo en el servidor
                }
            } catch (e: Exception) {
                _error.value = "Error inesperado: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun clearError() {
        _error.value = null
    }
    
    private fun updateLocalRoute(id: String, origin: String, destination: String, description: String) {
        val currentRoutes = _routes.value
        val updatedRoutes = currentRoutes.map { route ->
            if (route.id == id) {
                route.copy(
                    origin = origin,
                    destination = destination,
                    description = description
                )
            } else {
                route
            }
        }
        _routes.value = updatedRoutes
    }
} 