package com.example.livinnshubstay.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ReportProblem
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

import androidx.navigation.compose.rememberNavController
import com.example.livinnshubstay.navigation.BottomNavItem
import com.example.livinnshubstay.ui.components.ActionButton
import com.example.livinnshubstay.ui.components.CircularIconButton
import com.example.livinnshubstay.ui.components.DarkCard
import com.example.livinnshubstay.ui.components.HeaderWithHighlight
import com.example.livinnshubstay.ui.components.PrimaryActionFAB
import com.example.livinnshubstay.ui.components.Tag
import com.example.livinnshubstay.ui.theme.LivinnsHubStayTheme
import com.example.livinnshubstay.ui.theme.NeonGreen
import com.example.livinnshubstay.ui.theme.TextGrey
import com.example.livinnshubstay.ui.theme.VibrantPurple

@Composable
fun HomeScreen(navController: NavController = rememberNavController()) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                onNavigate = { route ->
                    navController.navigate(route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(24.dp))
                
                // Header section
                HeaderWithHighlight(
                    text = "Find your",
                    highlightText = "perfect stay",
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                
                ActionButton(
                    text = "Explore Now",
                    onClick = { /* Handle click */ },
                    modifier = Modifier.padding(bottom = 24.dp)
                )
                
                // Quick action buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CircularIconButton(
                        icon = Icons.Default.Home,
                        contentDescription = "Hotels",
                        onClick = { /* Handle click */ }
                    )
                    
                    CircularIconButton(
                        icon = Icons.Outlined.LocationOn,
                        contentDescription = "Locations",
                        onClick = { /* Handle click */ },
                        showNotification = true
                    )
                    
                    CircularIconButton(
                        icon = Icons.Default.Search,
                        contentDescription = "Search",
                        onClick = { /* Handle click */ }
                    )
                    
                    CircularIconButton(
                        icon = Icons.Default.Person,
                        contentDescription = "Profile",
                        onClick = { /* Handle click */ }
                    )
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Featured section
                Text(
                    text = "Featured Stays",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            
            // Featured cards
            items(3) { index ->
                FeaturedStayCard(
                    title = "Luxury Suite ${index + 1}",
                    location = "Downtown Area",
                    price = "$${120 + index * 20}/night",
                    rating = 4.8f,
                    isOffer = index % 2 == 0
                )
            }
            
            item {
                Spacer(modifier = Modifier.height(16.dp))
                
                // Popular section
                Text(
                    text = "Popular Destinations",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(5) { index ->
                        DestinationCard(
                            name = "Destination ${index + 1}",
                            properties = "${index + 5} properties"
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

@Composable
fun FeaturedStayCard(
    title: String,
    location: String,
    price: String,
    rating: Float,
    isOffer: Boolean
) {
    DarkCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                // This would be an image in a real app
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .background(VibrantPurple.copy(alpha = 0.3f))
                )
                
                if (isOffer) {
                    Tag(
                        text = "20% OFF",
                        isOffer = true,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(8.dp)
                    )
                }
                
                IconButton(
                    onClick = { /* Handle click */ },
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = Color.White
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium
                    )
                    
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.LocationOn,
                            contentDescription = null,
                            tint = TextGrey,
                            modifier = Modifier.size(16.dp)
                        )
                        
                        Spacer(modifier = Modifier.width(4.dp))
                        
                        Text(
                            text = location,
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextGrey
                        )
                    }
                }
                
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Star,
                        contentDescription = null,
                        tint = NeonGreen,
                        modifier = Modifier.size(16.dp)
                    )
                    
                    Spacer(modifier = Modifier.width(4.dp))
                    
                    Text(
                        text = rating.toString(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = price,
                    style = MaterialTheme.typography.titleMedium,
                    color = NeonGreen
                )
                
                ActionButton(
                    text = "Book Now",
                    onClick = { /* Handle click */ }
                )
            }
        }
    }
}

@Composable
fun DestinationCard(
    name: String,
    properties: String
) {
    DarkCard(
        modifier = Modifier.width(160.dp)
    ) {
        Column {
            // This would be an image in a real app
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(VibrantPurple.copy(alpha = 0.3f))
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = name,
                style = MaterialTheme.typography.titleSmall
            )
            
            Text(
                text = properties,
                style = MaterialTheme.typography.bodySmall,
                color = TextGrey
            )
        }
    }
}

@Composable
fun BottomNavigationBar(
    onNavigate: (String) -> Unit = {},
    selectedItem: BottomNavItem = BottomNavItem.HOME
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
            IconButton(onClick = { onNavigate(BottomNavItem.ACCOUNT.route) }) {
                Icon(
                    imageVector = BottomNavItem.ACCOUNT.icon,
                    contentDescription = BottomNavItem.ACCOUNT.title,
                    tint = if (selectedItem == BottomNavItem.ACCOUNT) VibrantPurple else TextGrey
                )
            }
            
            // Maintenance
            IconButton(onClick = { onNavigate(BottomNavItem.MAINTENANCE.route) }) {
                Icon(
                    imageVector = BottomNavItem.MAINTENANCE.icon,
                    contentDescription = BottomNavItem.MAINTENANCE.title,
                    tint = if (selectedItem == BottomNavItem.MAINTENANCE) VibrantPurple else TextGrey
                )
            }
            
            // Home (Center FAB)
            PrimaryActionFAB(
                icon = BottomNavItem.HOME.icon,
                contentDescription = BottomNavItem.HOME.title,
                onClick = { onNavigate(BottomNavItem.HOME.route) },
                isSelected = selectedItem == BottomNavItem.HOME
            )
            
            // Complaint
            IconButton(onClick = { onNavigate(BottomNavItem.COMPLAINT.route) }) {
                Icon(
                    imageVector = BottomNavItem.COMPLAINT.icon,
                    contentDescription = BottomNavItem.COMPLAINT.title,
                    tint = if (selectedItem == BottomNavItem.COMPLAINT) VibrantPurple else TextGrey
                )
            }
            
            // Profile
            IconButton(onClick = { onNavigate(BottomNavItem.PROFILE.route) }) {
                Icon(
                    imageVector = BottomNavItem.PROFILE.icon,
                    contentDescription = BottomNavItem.PROFILE.title,
                    tint = if (selectedItem == BottomNavItem.PROFILE) VibrantPurple else TextGrey
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    LivinnsHubStayTheme {
        HomeScreen()
    }
}