package com.example.livinnshubstay.data

data class User(
    val id: String = "",
    val email: String = "",
    val name: String = "",
    val profileImage: String = ""
)

data class AuthState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val errorMessage: String = "",
    val isAuthenticated: Boolean = false
)

sealed class AuthEvent {
    data class Login(val email: String, val password: String) : AuthEvent()
    object Logout : AuthEvent()
    object ClearError : AuthEvent()
}

enum class NavigationItem {
    DASHBOARD,
    NOTIFICATIONS,
    SETTINGS
}

data class DashboardCard(
    val title: String,
    val icon: String,
    val route: String
)
