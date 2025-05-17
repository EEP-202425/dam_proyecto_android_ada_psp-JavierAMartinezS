package com.example.proyectofinalandroid.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectofinalandroid.data.Elemento
import com.example.proyectofinalandroid.data.FakeServer
import kotlinx.coroutines.*

class ElementosViewModel : ViewModel() {
    var elementos by mutableStateOf<List<Elemento>>(emptyList())
        private set

    var cargando by mutableStateOf(false)
        private set

    init {
        cargarElementos()
    }

    fun cargarElementos() {
        cargando = true
        viewModelScope.launch {
            elementos = FakeServer.obtenerElementos()
            cargando = false
        }
    }

    fun agregarElemento(nombre: String, onDone: () -> Unit) {
        viewModelScope.launch {
            FakeServer.agregarElemento(Elemento(nombre))
            cargarElementos()
            onDone()
        }
    }
}