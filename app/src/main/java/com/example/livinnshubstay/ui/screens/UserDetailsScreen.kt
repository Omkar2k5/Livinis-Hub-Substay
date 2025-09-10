package com.example.livinnshubstay.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.ContactPhone
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.livinnshubstay.ui.theme.DeepDarkBackground
import com.example.livinnshubstay.ui.theme.DarkGrey
import com.example.livinnshubstay.ui.theme.LivinnsHubStayTheme
import com.example.livinnshubstay.ui.theme.NeonGreen
import com.example.livinnshubstay.ui.theme.TextGrey
import com.example.livinnshubstay.ui.theme.TextWhite
import com.example.livinnshubstay.ui.theme.VibrantPurple
import com.example.livinnshubstay.viewmodel.AuthViewModel

@Composable
fun UserDetailsScreen(
    email: String,
    password: String,
    authViewModel: AuthViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    // User details form fields
    var phoneNumber by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var aadharNumber by remember { mutableStateOf("") }
    var emergencyContactName by remember { mutableStateOf("") }
    var emergencyContactPhone by remember { mutableStateOf("") }
    var guardianName by remember { mutableStateOf("") }
    var guardianPhone by remember { mutableStateOf("") }
    
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isVisible by remember { mutableStateOf(false) }
    var currentStep by remember { mutableStateOf(1) }
    val totalSteps = 3
    
    // Trigger animations on screen load
    LaunchedEffect(Unit) {
        isVisible = true
    }
    
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(DeepDarkBackground)
    ) {
        // Back button
        IconButton(
            onClick = onNavigateBack,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = TextWhite
            )
        }
        
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn() + slideInVertically(initialOffsetY = { it / 2 }),
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
                    .padding(top = 40.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Title
                Text(
                    text = buildAnnotatedString {
                        append("Complete Your ")
                        withStyle(
                            style = SpanStyle(
                                color = NeonGreen,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append("Profile")
                        }
                    },
                    style = MaterialTheme.typography.displayMedium,
                    color = TextWhite,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                
                Text(
                    text = "Step $currentStep of $totalSteps",
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextGrey,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                
                // Progress indicator
                LinearProgressIndicator(
                    progress = currentStep.toFloat() / totalSteps,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp),
                    color = NeonGreen,
                    trackColor = DarkGrey
                )
                
                // User Details Form
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = DarkGrey
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Step 1: Basic Information
                        AnimatedVisibility(visible = currentStep == 1) {
                            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                                Text(
                                    text = "Basic Information",
                                    style = MaterialTheme.typography.titleLarge,
                                    color = TextWhite
                                )
                                
                                // Phone Number
                                ThemeTextField(
                                    value = phoneNumber,
                                    onValueChange = { 
                                        if (it.length <= 10 && it.all { char -> char.isDigit() }) {
                                            phoneNumber = it 
                                        }
                                    },
                                    label = "Phone Number",
                                    leadingIcon = Icons.Default.Phone,
                                    keyboardType = KeyboardType.Phone
                                )
                                
                                // Address
                                ThemeTextField(
                                    value = address,
                                    onValueChange = { address = it },
                                    label = "Current Address",
                                    leadingIcon = Icons.Default.Home,
                                    keyboardType = KeyboardType.Text
                                )
                                
                                // Aadhar Number
                                ThemeTextField(
                                    value = aadharNumber,
                                    onValueChange = { 
                                        if (it.length <= 12 && it.all { char -> char.isDigit() }) {
                                            aadharNumber = it 
                                        }
                                    },
                                    label = "Aadhar Card Number",
                                    leadingIcon = Icons.Default.Badge,
                                    keyboardType = KeyboardType.Number
                                )
                            }
                        }
                        
                        // Step 2: Emergency Contact
                        AnimatedVisibility(visible = currentStep == 2) {
                            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                                Text(
                                    text = "Emergency Contact",
                                    style = MaterialTheme.typography.titleLarge,
                                    color = TextWhite
                                )
                                
                                // Emergency Contact Name
                                ThemeTextField(
                                    value = emergencyContactName,
                                    onValueChange = { emergencyContactName = it },
                                    label = "Contact Name",
                                    leadingIcon = Icons.Default.Person,
                                    keyboardType = KeyboardType.Text
                                )
                                
                                // Emergency Contact Phone
                                ThemeTextField(
                                    value = emergencyContactPhone,
                                    onValueChange = { 
                                        if (it.length <= 10 && it.all { char -> char.isDigit() }) {
                                            emergencyContactPhone = it 
                                        }
                                    },
                                    label = "Contact Phone",
                                    leadingIcon = Icons.Default.Call,
                                    keyboardType = KeyboardType.Phone
                                )
                            }
                        }
                        
                        // Step 3: Guardian Details
                        AnimatedVisibility(visible = currentStep == 3) {
                            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                                Text(
                                    text = "Guardian Details",
                                    style = MaterialTheme.typography.titleLarge,
                                    color = TextWhite
                                )
                                
                                // Guardian Name
                                ThemeTextField(
                                    value = guardianName,
                                    onValueChange = { guardianName = it },
                                    label = "Guardian Name",
                                    leadingIcon = Icons.Default.Person,
                                    keyboardType = KeyboardType.Text
                                )
                                
                                // Guardian Phone
                                ThemeTextField(
                                    value = guardianPhone,
                                    onValueChange = { 
                                        if (it.length <= 10 && it.all { char -> char.isDigit() }) {
                                            guardianPhone = it 
                                        }
                                    },
                                    label = "Guardian Phone",
                                    leadingIcon = Icons.Default.ContactPhone,
                                    keyboardType = KeyboardType.Phone
                                )
                            }
                        }
                        
                        // Error message
                        if (errorMessage != null) {
                            Text(
                                text = errorMessage!!,
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Navigation buttons
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            // Back button (except on first step)
                            if (currentStep > 1) {
                                ThemeButton(
                                    text = "Previous",
                                    onClick = {
                                        errorMessage = null
                                        currentStep--
                                    },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                            
                            // Next/Submit button
                            ThemeButton(
                                text = if (currentStep < totalSteps) "Next" else "Submit",
                                onClick = {
                                    when (currentStep) {
                                        1 -> {
                                            // Validate Step 1
                                            when {
                                                phoneNumber.length != 10 -> {
                                                    errorMessage = "Please enter a valid 10-digit phone number"
                                                }
                                                address.isBlank() -> {
                                                    errorMessage = "Please enter your address"
                                                }
                                                aadharNumber.length != 12 -> {
                                                    errorMessage = "Please enter a valid 12-digit Aadhar number"
                                                }
                                                else -> {
                                                    errorMessage = null
                                                    currentStep++
                                                }
                                            }
                                        }
                                        2 -> {
                                            // Validate Step 2
                                            when {
                                                emergencyContactName.isBlank() -> {
                                                    errorMessage = "Please enter emergency contact name"
                                                }
                                                emergencyContactPhone.length != 10 -> {
                                                    errorMessage = "Please enter a valid 10-digit phone number"
                                                }
                                                else -> {
                                                    errorMessage = null
                                                    currentStep++
                                                }
                                            }
                                        }
                                        3 -> {
                                            // Validate Step 3 and submit
                                            when {
                                                guardianName.isBlank() -> {
                                                    errorMessage = "Please enter guardian name"
                                                }
                                                guardianPhone.length != 10 -> {
                                                    errorMessage = "Please enter a valid 10-digit phone number"
                                                }
                                                else -> {
                                                    // All steps validated, create user account
                                                    authViewModel.registerUser(
                                                        email = email,
                                                        password = password,
                                                        name = "New User", // In a real app, this would come from the sign-up form
                                                        phoneNumber = phoneNumber,
                                                        address = address,
                                                        aadharNumber = aadharNumber,
                                                        emergencyContactName = emergencyContactName,
                                                        emergencyContactPhone = emergencyContactPhone,
                                                        guardianName = guardianName,
                                                        guardianPhone = guardianPhone
                                                    )
                                                    
                                                    // Navigate to home
                                                    onNavigateToHome()
                                                }
                                            }
                                        }
                                    }
                                },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserDetailsScreenPreview() {
    LivinnsHubStayTheme {
        UserDetailsScreen(
            email = "user@example.com",
            password = "password123",
            authViewModel = AuthViewModel(),
            onNavigateBack = {},
            onNavigateToHome = {}
        )
    }
}