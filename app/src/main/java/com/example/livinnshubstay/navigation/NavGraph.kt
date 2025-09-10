package com.example.livinnshubstay.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.livinnshubstay.ui.screens.AccountScreen
import com.example.livinnshubstay.ui.screens.ComplaintScreen
import com.example.livinnshubstay.ui.screens.HomeScreen
import com.example.livinnshubstay.ui.screens.LoginScreen
import com.example.livinnshubstay.ui.screens.MaintenanceScreen
import com.example.livinnshubstay.ui.screens.ProfileScreen
import com.example.livinnshubstay.ui.screens.SignUpScreen
import com.example.livinnshubstay.ui.screens.UserDetailsScreen
import com.example.livinnshubstay.viewmodel.AuthViewModel

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object SignUp : Screen("signup")
    object UserDetails : Screen("user_details/{email}/{password}") {
        fun createRoute(email: String, password: String): String {
            return "user_details/$email/$password"
        }
    }
    object Home : Screen("home")
    object Account : Screen("account")
    object Maintenance : Screen("maintenance")
    object Complaint : Screen("complaint")
    object Profile : Screen("profile")
}

@Composable
fun NavGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel = viewModel()
) {
    NavHost(
        navController = navController,
        // Changed to start with the Login screen
        startDestination = Screen.Login.route
    ) {
        composable(route = Screen.Login.route) {
            LoginScreen(
                authViewModel = authViewModel,
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        // Clear the back stack to prevent user from going back to login
                        popUpTo(Screen.Login.route) {
                            inclusive = true
                        }
                        // Avoid multiple copies of home screen
                        launchSingleTop = true
                    }
                },
                onNavigateToSignUp = {
                    navController.navigate(Screen.SignUp.route)
                }
            )
        }
        
        composable(route = Screen.SignUp.route) {
            SignUpScreen(
                authViewModel = authViewModel,
                onNavigateToLogin = {
                    navController.popBackStack()
                },
                onNavigateToUserDetails = { email, password ->
                    navController.navigate(Screen.UserDetails.createRoute(email, password))
                }
            )
        }
        
        composable(
            route = Screen.UserDetails.route,
            arguments = listOf(
                navArgument("email") { type = NavType.StringType },
                navArgument("password") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val password = backStackEntry.arguments?.getString("password") ?: ""
            
            UserDetailsScreen(
                email = email,
                password = password,
                authViewModel = authViewModel,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        // Clear the back stack to prevent user from going back
                        popUpTo(0) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }
        
        // Dashboard screen removed - using Home screen instead
        
        composable(route = Screen.Home.route) {
            // Home screen with the updated theme
            HomeScreen()
        }
        
        composable(route = Screen.Account.route) {
            AccountScreen()
        }
        
        composable(route = Screen.Maintenance.route) {
            MaintenanceScreen()
        }
        
        composable(route = Screen.Complaint.route) {
            ComplaintScreen()
        }
        
        composable(route = Screen.Profile.route) {
            ProfileScreen()
        }
    }
}
