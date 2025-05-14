package com.example.proyectofinalandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyectofinalandroid.ui.screens.RutaFormScreen
import com.example.proyectofinalandroid.ui.screens.RutaScreen
import com.example.proyectofinalandroid.ui.theme.ProyectoFinalAndroidTheme
import com.example.proyectofinalandroid.ui.viewmodel.RutaViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProyectoFinalAndroidTheme {
                RutaApp()
            }
        }
    }
}

@Composable
fun RutaApp() {
    val navController = rememberNavController()
    val viewModel: RutaViewModel = viewModel()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            RutaScreen(navController = navController, viewModel = viewModel)
        }
        composable("agregar") {
            RutaFormScreen(
                navController = navController,
                viewModel = viewModel,
                isAdd = true
            )
        }
        composable("eliminar") {
            RutaFormScreen(
                navController = navController,
                viewModel = viewModel,
                isAdd = false
            )
        }
    }
}