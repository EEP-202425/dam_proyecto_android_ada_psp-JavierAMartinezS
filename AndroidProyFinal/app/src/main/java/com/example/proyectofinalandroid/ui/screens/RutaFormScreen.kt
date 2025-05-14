package com.example.proyectofinalandroid.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.proyectofinalandroid.ui.viewmodel.RutaViewModel

@Composable
fun RutaFormScreen(
    navController: NavController,
    viewModel: RutaViewModel,
    isAdd: Boolean
) {
    var origen by remember { mutableStateOf("") }
    var destino by remember { mutableStateOf("") }
    var id by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = if (isAdd) "Añadir Nueva Ruta" else "Eliminar Ruta",
            style = MaterialTheme.typography.headlineMedium
        )

        if (isAdd) {
            OutlinedTextField(
                value = origen,
                onValueChange = { origen = it },
                label = { Text("Origen") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = destino,
                onValueChange = { destino = it },
                label = { Text("Destino") },
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            OutlinedTextField(
                value = id,
                onValueChange = { id = it },
                label = { Text("ID de la Ruta") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Button(
            onClick = {
                if (isAdd) {
                    if (origen.isNotBlank() && destino.isNotBlank()) {
                        viewModel.addRuta(origen, destino)
                        navController.navigateUp()
                    }
                } else {
                    if (id.isNotBlank()) {
                        viewModel.deleteRuta(id.toIntOrNull() ?: return@Button)
                        navController.navigateUp()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isAdd) "Añadir" else "Eliminar")
        }

        Button(
            onClick = { navController.navigateUp() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cancelar")
        }
    }
} 