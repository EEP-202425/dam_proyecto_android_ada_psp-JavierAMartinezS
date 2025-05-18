package com.example.starlineapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.starlineapp.navigation.Screen
import com.example.starlineapp.ui.screens.*
import com.example.starlineapp.ui.theme.StarlineAppTheme
import com.example.starlineapp.viewmodel.AuthViewModel
import com.example.starlineapp.viewmodel.RouteViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StarlineAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainNavigation()
                }
            }
        }
    }
}

@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    val routeViewModel: RouteViewModel = viewModel()
    val authViewModel: AuthViewModel = viewModel()
    val routes by routeViewModel.routes.collectAsState()
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginClick = { email, password ->
                    if (authViewModel.login(email, password)) {
                        navController.navigate(Screen.RouteList.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    } else {
                        Toast.makeText(context, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                    }
                },
                onRegisterClick = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                onRegisterClick = { nombre, email, password, confirmPassword ->
                    if (password == confirmPassword) {
                        if (authViewModel.register(nombre, email, password)) {
                            navController.navigate(Screen.RouteList.route) {
                                popUpTo(Screen.Login.route) { inclusive = true }
                            }
                        } else {
                            Toast.makeText(context, "El email ya está registrado", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(context, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                    }
                },
                onBackToLoginClick = {
                    navController.navigateUp()
                }
            )
        }

        composable(Screen.RouteList.route) {
            RouteListScreen(
                routes = routes,
                onAddRouteClick = {
                    navController.navigate(Screen.AddRoute.route)
                },
                onRouteClick = { routeId ->
                    navController.navigate(Screen.RouteDetail.createRoute(routeId))
                },
                onDeleteRoute = { routeId ->
                    routeViewModel.deleteRoute(routeId)
                },
                onEditRoute = { routeId ->
                    navController.navigate(Screen.EditRoute.createRoute(routeId))
                }
            )
        }

        composable(Screen.AddRoute.route) {
            AddRouteScreen(
                onSaveRoute = { origin, destination, description ->
                    routeViewModel.addRoute(origin, destination, description)
                },
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }

        composable(
            route = Screen.RouteDetail.route,
            arguments = listOf(
                navArgument("routeId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val routeId = backStackEntry.arguments?.getString("routeId")
            routeId?.let { id ->
                val route = routeViewModel.getRoute(id)
                route?.let {
                    RouteDetailScreen(
                        route = it,
                        onNavigateBack = {
                            navController.navigateUp()
                        },
                        onDeleteRoute = { routeId ->
                            routeViewModel.deleteRoute(routeId)
                            navController.navigateUp()
                        }
                    )
                }
            }
        }

        composable(
            route = Screen.EditRoute.route,
            arguments = listOf(
                navArgument("routeId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val routeId = backStackEntry.arguments?.getString("routeId")
            routeId?.let { id ->
                val route = routeViewModel.getRoute(id)
                route?.let {
                    EditRouteScreen(
                        route = it,
                        onSaveRoute = { origin, destination, description ->
                            routeViewModel.updateRoute(id, origin, destination, description)
                            navController.navigateUp()
                        },
                        onNavigateBack = {
                            navController.navigateUp()
                        }
                    )
                }
            }
        }
    }
}