package com.example.starlineapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import android.widget.Toast

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditRouteScreen(
    ruta: com.example.starlineapp.model.Ruta,
    onSaveRoute: (origin: String, destination: String, description: String) -> Unit,
    onNavigateBack: () -> Unit
) {
    var origin by remember { mutableStateOf(ruta.origin) }
    var destination by remember { mutableStateOf(ruta.destination) }
    var description by remember { mutableStateOf(ruta.description) }
    var isError by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar Ruta") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = origin,
                onValueChange = { 
                    origin = it
                    isError = false
                },
                label = { Text("Origen") },
                isError = isError && origin.isBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            OutlinedTextField(
                value = destination,
                onValueChange = { 
                    destination = it
                    isError = false
                },
                label = { Text("Destino") },
                isError = isError && destination.isBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descripción (opcional)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(
                    onClick = onNavigateBack,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                ) {
                    Text("Cancelar")
                }

                Button(
                    onClick = {
                        if (origin.isBlank() || destination.isBlank()) {
                            isError = true
                            Toast.makeText(
                                context,
                                "Por favor, completa los campos obligatorios",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            try {
                                onSaveRoute(origin.trim(), destination.trim(), description.trim())
                                onNavigateBack()
                            } catch (e: Exception) {
                                Toast.makeText(
                                    context,
                                    "Error al guardar la ruta: ${e.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                ) {
                    Text("Guardar")
                }
            }
        }
    }
} 