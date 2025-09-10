package com.example.livinnshubstay.data

data class User(
    val id: String = "",
    val email: String = "",
    val name: String = "",
    val profileImage: String = "",
    // Additional user details
    val phoneNumber: String = "",
    val address: String = "",
    val aadharNumber: String = "",
    val emergencyContact: EmergencyContact = EmergencyContact(),
    val guardian: Guardian = Guardian()
)

data class EmergencyContact(
    val name: String = "",
    val phoneNumber: String = ""
)

data class Guardian(
    val name: String = "",
    val phoneNumber: String = ""
)

data class AuthState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val errorMessage: String = "",
    val isAuthenticated: Boolean = false
)

sealed class AuthEvent {
    data class Login(val email: String, val password: String) : AuthEvent()
    data class Register(
        val email: String, 
        val password: String,
        val name: String,
        val phoneNumber: String = "",
        val address: String = "",
        val aadharNumber: String = "",
        val emergencyContact: EmergencyContact = EmergencyContact(),
        val guardian: Guardian = Guardian()
    ) : AuthEvent()
    object Logout : AuthEvent()
    object ClearError : AuthEvent()
}

enum class NavigationItem {
    DASHBOARD,
    NOTIFICATIONS,
    SETTINGS,
    PROFILE,
    HOME
}

data class DashboardCard(
    val title: String,
    val icon: String,
    val route: String
)
