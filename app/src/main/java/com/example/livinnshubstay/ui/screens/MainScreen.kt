package com.example.livinnshubstay.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ReportProblem
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Alignment
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.livinnshubstay.navigation.Screen
import com.example.livinnshubstay.ui.components.PrimaryActionFAB
import com.example.livinnshubstay.ui.theme.LivinnsHubStayTheme
import com.example.livinnshubstay.ui.theme.TextGrey
import com.example.livinnshubstay.ui.theme.VibrantPurple

enum class BottomNavItem {
    HOME, ACCOUNT, MAINTENANCE, COMPLAINT, PROFILE
}

@Composable
fun MainScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(BottomNavItem.HOME) }
    
    Scaffold(
        bottomBar = {
            MainBottomNavigationBar(
                selectedTab = selectedTab,
                onTabSelected = { tab ->
                    selectedTab = tab
                    when (tab) {
                        BottomNavItem.HOME -> navController.navigate(Screen.Home.route)
                        BottomNavItem.ACCOUNT -> navController.navigate(Screen.Account.route)
                        BottomNavItem.MAINTENANCE -> navController.navigate(Screen.Maintenance.route)
                        BottomNavItem.COMPLAINT -> navController.navigate(Screen.Complaint.route)
                        BottomNavItem.PROFILE -> navController.navigate(Screen.Profile.route)
                    }
                }
            )
        },
        modifier = modifier
    ) { paddingValues ->
        // Content will be handled by the NavHost
        // This is just a container for the bottom navigation
    }
}

@Composable
fun MainBottomNavigationBar(
    selectedTab: BottomNavItem,
    onTabSelected: (BottomNavItem) -> Unit
) {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Account
            IconButton(onClick = { onTabSelected(BottomNavItem.ACCOUNT) }) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Account",
                    tint = if (selectedTab == BottomNavItem.ACCOUNT) VibrantPurple else TextGrey
                )
            }
            
            // Maintenance
            IconButton(onClick = { onTabSelected(BottomNavItem.MAINTENANCE) }) {
                Icon(
                    imageVector = Icons.Default.Build,
                    contentDescription = "Maintenance",
                    tint = if (selectedTab == BottomNavItem.MAINTENANCE) VibrantPurple else TextGrey
                )
            }
            
            // Home (Center FAB)
            PrimaryActionFAB(
                icon = Icons.Default.Home,
                contentDescription = "Home",
                onClick = { onTabSelected(BottomNavItem.HOME) },
                isSelected = selectedTab == BottomNavItem.HOME
            )
            
            // Complaint
            IconButton(onClick = { onTabSelected(BottomNavItem.COMPLAINT) }) {
                Icon(
                    imageVector = Icons.Default.ReportProblem,
                    contentDescription = "Complaint",
                    tint = if (selectedTab == BottomNavItem.COMPLAINT) VibrantPurple else TextGrey
                )
            }
            
            // Profile
            IconButton(onClick = { onTabSelected(BottomNavItem.PROFILE) }) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Profile",
                    tint = if (selectedTab == BottomNavItem.PROFILE) VibrantPurple else TextGrey
                )
            }
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    LivinnsHubStayTheme {
        MainScreen(navController = rememberNavController())
    }
}