package com.example.livinnshubstay.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ReportProblem
import androidx.compose.ui.graphics.vector.ImageVector

enum class BottomNavItem(val route: String, val icon: ImageVector, val title: String) {
    HOME("home", Icons.Default.Home, "Home"),
    ACCOUNT("account", Icons.Default.Person, "Account"),
    MAINTENANCE("maintenance", Icons.Default.Build, "Maintenance"),
    COMPLAINT("complaint", Icons.Default.ReportProblem, "Complaint"),
    PROFILE("profile", Icons.Default.AccountCircle, "Profile")
}