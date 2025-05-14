package com.example.proyectofinalandroid.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.proyectofinalandroid.data.model.Ruta
import com.example.proyectofinalandroid.ui.viewmodel.RutaViewModel

@Composable
fun RutaScreen(
    navController: NavController,
    viewModel: RutaViewModel
) {
    val rutas by viewModel.rutas.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { navController.navigate("agregar") }) {
                Text("Añadir Ruta")
            }
            Button(onClick = { navController.navigate("eliminar") }) {
                Text("Eliminar Ruta")
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(rutas) { ruta ->
                RutaCard(ruta = ruta)
            }
        }
    }
}

@Composable
fun RutaCard(ruta: Ruta) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "ID: ${ruta.id}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Origen: ${ruta.origen}",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Destino: ${ruta.destino}",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
} 