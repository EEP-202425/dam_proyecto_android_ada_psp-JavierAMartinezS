package com.example.starlineapp.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object RouteList : Screen("routeList")
    object AddRoute : Screen("addRoute")
    object EditRoute : Screen("editRoute/{routeId}") {
        fun createRoute(routeId: String) = "editRoute/$routeId"
    }
    object RouteDetail : Screen("routeDetail/{routeId}") {
        fun createRoute(routeId: String) = "routeDetail/$routeId"
    }
} 