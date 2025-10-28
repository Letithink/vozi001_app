package com.example.vozi001.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.vozi001.ui.composables.main.HomeScreen
import com.example.vozi001.ui.composables.main.ProfileScreen
import com.example.vozi001.ui.composables.main.SettingsScreen
import com.example.vozi001.ui.composables.main.StatisticsScreen

// Definir las rutas de navegación
sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Statistics : Screen("statistics")
    object Profile : Screen("profile")
    object Settings : Screen("settings")
}

@Composable
fun NavGraph(
    navController: NavHostController,
    onSignOut: () -> Unit,
    onStartRecording: () -> Unit,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onStartRecording = onStartRecording
            )
        }
        composable(Screen.Statistics.route) {
            StatisticsScreen()
        }
        composable(Screen.Profile.route) {
            ProfileScreen()
        }
        composable(Screen.Settings.route) {
            SettingsScreen(
                onSignOut = onSignOut
            )
        }
    }
}

@Composable
fun HomeScreen(onStartRecording: () -> Unit) {
    TODO("Not yet implemented")
}

@Composable
fun StatisticsScreen() {
    TODO("Not yet implemented")
}

@Composable
fun SettingsScreen(onSignOut: () -> Unit) {
    TODO("Not yet implemented")
}

@Composable
fun ProfileScreen() {
    TODO("Not yet implemented")
}

fun composable(route: String, function: Any) {}
