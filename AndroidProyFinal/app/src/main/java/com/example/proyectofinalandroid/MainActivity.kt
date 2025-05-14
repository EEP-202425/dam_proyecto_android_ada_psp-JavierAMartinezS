package com.example.proyectofinalandroid

import ListaPantalla
import AgregarPantalla
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyectofinalandroid.viewmodel.ElementosViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val viewModel: ElementosViewModel = viewModel()

            NavHost(navController = navController, startDestination = "lista") {
                composable("lista") { ListaPantalla(navController, viewModel) }
                composable("agregar") { AgregarPantalla(navController, viewModel) }
            }
        }
    }
}