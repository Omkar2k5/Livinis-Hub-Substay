package com.example.livinnshubstay.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.livinnshubstay.ui.screens.DashboardScreen
import com.example.livinnshubstay.ui.screens.HomeScreen
import com.example.livinnshubstay.ui.screens.LoginScreen
import com.example.livinnshubstay.viewmodel.AuthViewModel

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Dashboard : Screen("dashboard")
    object Home : Screen("home")
}

@Composable
fun NavGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel = viewModel()
) {
    NavHost(
        navController = navController,
        // Changed to start with the Home screen to showcase the new theme
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Login.route) {
            LoginScreen(
                authViewModel = authViewModel,
                onNavigateToDashboard = {
                    navController.navigate(Screen.Dashboard.route) {
                        // Clear the back stack to prevent user from going back to login
                        popUpTo(Screen.Login.route) {
                            inclusive = true
                        }
                        // Avoid multiple copies of dashboard
                        launchSingleTop = true
                    }
                }
            )
        }
        
        composable(route = Screen.Dashboard.route) {
            DashboardScreen(
                authViewModel = authViewModel,
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route) {
                        // Clear the back stack completely
                        popUpTo(0) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }
        
        composable(route = Screen.Home.route) {
            // New Home screen with the updated theme
            HomeScreen()
        }
    }
}
