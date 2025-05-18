package com.example.starlineapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starlineapp.R
import com.example.starlineapp.model.Route
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class RouteViewModel : ViewModel() {
    private val _routes = MutableStateFlow<List<Route>>(emptyList())
    val routes: StateFlow<List<Route>> = _routes.asStateFlow()

    fun addRoute(origin: String, destination: String, description: String = "") {
        val newRoute = Route(
            id = UUID.randomUUID().toString(),
            origin = origin,
            destination = destination,
            description = description,
            imageResId = R.drawable.tren_starline
        )
        viewModelScope.launch {
            _routes.value = _routes.value + newRoute
        }
    }

    fun deleteRoute(id: String) {
        viewModelScope.launch {
            _routes.value = _routes.value.filter { it.id != id }
        }
    }

    fun getRoute(id: String): Route? {
        return _routes.value.find { it.id == id }
    }

    fun updateRoute(id: String, origin: String, destination: String, description: String) {
        viewModelScope.launch {
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
} 