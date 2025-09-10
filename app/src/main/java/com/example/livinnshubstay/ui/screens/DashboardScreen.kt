package com.example.livinnshubstay.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.livinnshubstay.data.NavigationItem
import com.example.livinnshubstay.data.User
import com.example.livinnshubstay.viewmodel.AuthViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    authViewModel: AuthViewModel,
    onNavigateToLogin: () -> Unit,
    modifier: Modifier = Modifier
) {
    val authState by authViewModel.authState.collectAsStateWithLifecycle()
    var selectedBottomNav by remember { mutableStateOf(NavigationItem.DASHBOARD) }
    var isVisible by remember { mutableStateOf(false) }
    
    // Dashboard cards data
    val dashboardCards = listOf(
        DashboardCardData("Rooms", Icons.Default.Hotel, "rooms"),
        DashboardCardData("Payments", Icons.Default.Payment, "payments"),
        DashboardCardData("Complaints", Icons.Default.ReportProblem, "complaints"),
        DashboardCardData("Inventory", Icons.Default.Inventory, "inventory"),
        DashboardCardData("Mess", Icons.Default.Restaurant, "mess"),
        DashboardCardData("Profile", Icons.Default.Person, "profile")
    )
    
    // Screen entry animation
    LaunchedEffect(Unit) {
        delay(100)
        isVisible = true
    }
    
    // Check if user is authenticated
    LaunchedEffect(authState.isAuthenticated) {
        if (!authState.isAuthenticated) {
            onNavigateToLogin()
        }
    }
    
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInHorizontally(
            animationSpec = tween(600),
            initialOffsetX = { it }
        ) + fadeIn(animationSpec = tween(600)),
        modifier = modifier.fillMaxSize()
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Dashboard",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFF1565C0),
                        titleContentColor = Color.White
                    ),
                    actions = {
                        IconButton(
                            onClick = { authViewModel.onEvent(com.example.livinnshubstay.data.AuthEvent.Logout) }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Logout,
                                contentDescription = "Logout",
                                tint = Color.White
                            )
                        }
                    }
                )
            },
            bottomBar = {
                BottomNavigationBar(
                    selectedItem = selectedBottomNav,
                    onItemSelected = { selectedBottomNav = it }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color(0xFFF5F5F5))
            ) {
                
                // Welcome Card
                WelcomeCard(
                    user = authState.user,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
                
                // Dashboard Grid
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    itemsIndexed(dashboardCards) { index, card ->
                        DashboardCardItem(
                            card = card,
                            index = index,
                            onClick = { 
                                // Handle card click navigation
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun WelcomeCard(
    user: User?,
    modifier: Modifier = Modifier
) {
    val scale by animateFloatAsState(
        targetValue = 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "welcome_card_scale"
    )
    
    Card(
        modifier = modifier.scale(scale),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF1565C0).copy(alpha = 0.1f),
                            Color(0xFF00838F).copy(alpha = 0.1f)
                        )
                    )
                )
                .padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = "Welcome back,",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = user?.name ?: "Guest",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1565C0)
                    )
                    Text(
                        text = user?.email ?: "",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
                
                // Profile Avatar
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(30.dp))
                        .background(Color(0xFF1565C0).copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile",
                        tint = Color(0xFF1565C0),
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun DashboardCardItem(
    card: DashboardCardData,
    index: Int,
    onClick: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    
    // Staggered animation
    val animationDelay = index * 100
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy
        ),
        label = "card_press_scale"
    )
    
    var isVisible by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        delay(animationDelay.toLong())
        isVisible = true
    }
    
    AnimatedVisibility(
        visible = isVisible,
        enter = scaleIn(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = Spring.StiffnessLow
            )
        ) + fadeIn(animationSpec = tween(300))
    ) {
        Card(
            modifier = Modifier
                .aspectRatio(1f)
                .scale(scale)
                .clickable {
                    isPressed = true
                    onClick()
                },
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.dp,
                pressedElevation = 6.dp
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                
                // Card Icon with background circle
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(30.dp))
                        .background(Color(0xFF1565C0).copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = card.icon,
                        contentDescription = card.title,
                        tint = Color(0xFF1565C0),
                        modifier = Modifier.size(28.dp)
                    )
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                // Card Title
                Text(
                    text = card.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    color = Color(0xFF1565C0)
                )
            }
        }
    }
    
    // Reset pressed state
    LaunchedEffect(isPressed) {
        if (isPressed) {
            delay(150)
            isPressed = false
        }
    }
}

@Composable
private fun BottomNavigationBar(
    selectedItem: NavigationItem,
    onItemSelected: (NavigationItem) -> Unit
) {
    NavigationBar(
        containerColor = Color.White,
        contentColor = Color(0xFF1565C0),
        modifier = Modifier.height(80.dp)
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Dashboard,
                    contentDescription = "Dashboard"
                )
            },
            label = { Text("Dashboard") },
            selected = selectedItem == NavigationItem.DASHBOARD,
            onClick = { onItemSelected(NavigationItem.DASHBOARD) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF1565C0),
                unselectedIconColor = Color.Gray,
                selectedTextColor = Color(0xFF1565C0),
                unselectedTextColor = Color.Gray,
                indicatorColor = Color(0xFF1565C0).copy(alpha = 0.1f)
            )
        )
        
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notifications"
                )
            },
            label = { Text("Notifications") },
            selected = selectedItem == NavigationItem.NOTIFICATIONS,
            onClick = { onItemSelected(NavigationItem.NOTIFICATIONS) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF1565C0),
                unselectedIconColor = Color.Gray,
                selectedTextColor = Color(0xFF1565C0),
                unselectedTextColor = Color.Gray,
                indicatorColor = Color(0xFF1565C0).copy(alpha = 0.1f)
            )
        )
        
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings"
                )
            },
            label = { Text("Settings") },
            selected = selectedItem == NavigationItem.SETTINGS,
            onClick = { onItemSelected(NavigationItem.SETTINGS) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF1565C0),
                unselectedIconColor = Color.Gray,
                selectedTextColor = Color(0xFF1565C0),
                unselectedTextColor = Color.Gray,
                indicatorColor = Color(0xFF1565C0).copy(alpha = 0.1f)
            )
        )
    }
}

data class DashboardCardData(
    val title: String,
    val icon: ImageVector,
    val route: String
)

@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    MaterialTheme {
        DashboardScreen(
            authViewModel = AuthViewModel().apply {
                // Mock authenticated user for preview
            },
            onNavigateToLogin = {}
        )
    }
}
