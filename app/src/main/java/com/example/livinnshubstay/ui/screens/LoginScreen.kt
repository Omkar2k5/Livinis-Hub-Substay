package com.example.livinnshubstay.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.livinnshubstay.data.AuthEvent
import com.example.livinnshubstay.ui.theme.*
import com.example.livinnshubstay.viewmodel.AuthViewModel

// Dummy credentials for testing
const val DUMMY_EMAIL = "user@example.com"
const val DUMMY_PASSWORD = "password123"

@Composable
fun LoginScreen(
    authViewModel: AuthViewModel,
    onNavigateToHome: () -> Unit,
    onNavigateToSignUp: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val authState by authViewModel.authState.collectAsStateWithLifecycle()
    
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isVisible by remember { mutableStateOf(false) }
    
    // Trigger animations on screen load
    LaunchedEffect(Unit) {
        isVisible = true
    }
    
    // Handle successful login
    LaunchedEffect(authState.isAuthenticated) {
        if (authState.isAuthenticated) {
            onNavigateToHome()
        }
    }
    
    // Shake animation for error
    val shakeOffset by animateFloatAsState(
        targetValue = if (authState.errorMessage.isNotEmpty()) 10f else 0f,
        animationSpec = repeatable(
            iterations = 3,
            animation = tween(100),
            repeatMode = RepeatMode.Reverse
        ),
        label = "shake"
    )
    
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(DeepDarkBackground)
    ) {
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(animationSpec = tween(800)) + slideInVertically(
                animationSpec = tween(800),
                initialOffsetY = { it / 2 }
            ),
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                
                // Logo Animation
                LogoAnimation(
                    modifier = Modifier
                        .size(120.dp)
                        .padding(bottom = 24.dp)
                )
                
                // App Title with Highlight
                Text(
                    text = buildAnnotatedString {
                        append("Welcome to ")
                        withStyle(
                            style = SpanStyle(
                                color = NeonGreen,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append("Livinns Hub")
                        }
                    },
                    style = MaterialTheme.typography.displayMedium,
                    color = TextWhite,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                
                Text(
                    text = "Sign in to continue",
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextGrey,
                    modifier = Modifier.padding(bottom = 32.dp)
                )
                
                // Login Form
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(x = shakeOffset.dp),
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
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        
                        // Email Field
                        ThemeTextField(
                            value = email,
                            onValueChange = { email = it },
                            label = "Email",
                            leadingIcon = Icons.Default.Email,
                            keyboardType = KeyboardType.Email,
                            hasError = authState.errorMessage.isNotEmpty()
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Password Field
                        ThemeTextField(
                            value = password,
                            onValueChange = { password = it },
                            label = "Password",
                            leadingIcon = Icons.Default.Lock,
                            isPassword = true,
                            passwordVisible = passwordVisible,
                            onPasswordVisibilityToggle = { passwordVisible = !passwordVisible },
                            hasError = authState.errorMessage.isNotEmpty()
                        )
                        
                        // Forgot Password
                        Text(
                            text = "Forgot Password?",
                            style = MaterialTheme.typography.bodyMedium,
                            color = VibrantPurple,
                            modifier = Modifier
                                .align(Alignment.End)
                                .padding(top = 8.dp)
                                .clickable {
                                    // Handle forgot password
                                }
                        )
                        
                        // Error Message
                        if (authState.errorMessage.isNotEmpty()) {
                            Text(
                                text = authState.errorMessage,
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp),
                                textAlign = TextAlign.Start
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        // Login Button
                        ThemeButton(
                            text = "Sign In",
                            onClick = {
                                // Try dummy credentials first
                                if (email == DUMMY_EMAIL && password == DUMMY_PASSWORD) {
                                    onNavigateToHome()
                                } else {
                                    // If not dummy credentials, use the view model
                                    authViewModel.onEvent(AuthEvent.Login(email.trim(), password))
                                }
                            },
                            isLoading = authState.isLoading,
                            modifier = Modifier.fillMaxWidth()
                        )
                        
                        // Demo credentials hint
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Demo: $DUMMY_EMAIL / $DUMMY_PASSWORD",
                            color = TextGrey,
                            style = MaterialTheme.typography.bodySmall,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                
                // Sign up text
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Don't have an account? ",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextGrey
                    )
                    
                    Text(
                        text = "Sign Up",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = NeonGreen,
                        modifier = Modifier.clickable { onNavigateToSignUp() }
                    )
                }
            }
        }
        
        // Success Animation Overlay
        if (authState.isAuthenticated) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                SuccessAnimation()
            }
        }
    }
}

@Composable
fun ThemeTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    leadingIcon: ImageVector,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false,
    passwordVisible: Boolean = false,
    onPasswordVisibilityToggle: () -> Unit = {},
    hasError: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        leadingIcon = {
            Icon(
                imageVector = leadingIcon,
                contentDescription = label,
                tint = if (hasError) MaterialTheme.colorScheme.error else VibrantPurple
            )
        },
        trailingIcon = if (isPassword) {
            {
                IconButton(onClick = onPasswordVisibilityToggle) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = if (passwordVisible) "Hide password" else "Show password",
                        tint = TextGrey
                    )
                }
            }
        } else null,
        visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = if (hasError) MaterialTheme.colorScheme.error else VibrantPurple,
            unfocusedBorderColor = if (hasError) MaterialTheme.colorScheme.error else TextGrey,
            focusedLabelColor = if (hasError) MaterialTheme.colorScheme.error else VibrantPurple,
            unfocusedLabelColor = TextGrey,
            cursorColor = VibrantPurple,
            focusedTextColor = TextWhite,
            unfocusedTextColor = TextWhite
        ),
        isError = hasError
    )
}

@Composable
fun ThemeButton(
    text: String,
    onClick: () -> Unit,
    isLoading: Boolean = false,
    modifier: Modifier = Modifier
) {
    val scale by animateFloatAsState(
        targetValue = if (isLoading) 0.95f else 1f,
        animationSpec = tween(150),
        label = "button_scale"
    )
    
    Button(
        onClick = onClick,
        modifier = modifier
            .height(56.dp)
            .scale(scale),
        enabled = !isLoading,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = VibrantPurple
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 4.dp,
            pressedElevation = 2.dp
        )
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = TextWhite,
                strokeWidth = 2.dp,
                modifier = Modifier.size(20.dp)
            )
        } else {
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium,
                color = TextWhite
            )
        }
    }
}

@Composable
fun LogoAnimation(modifier: Modifier = Modifier) {
    // Animated logo with pulsing effect
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_scale"
    )
    
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        // Background glow
        Box(
            modifier = Modifier
                .size(100.dp)
                .scale(scale)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            VibrantPurple.copy(alpha = 0.7f),
                            VibrantPurple.copy(alpha = 0.0f)
                        )
                    ),
                    shape = RoundedCornerShape(50.dp)
                )
        )
        
        // Logo text
        Text(
            text = "LH",
            style = MaterialTheme.typography.displayMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            color = NeonGreen,
            modifier = Modifier.scale(scale * 0.9f)
        )
    }
}

@Composable
fun SuccessAnimation() {
    val infiniteTransition = rememberInfiniteTransition(label = "success")
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "success_scale"
    )
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepDarkBackground.copy(alpha = 0.8f)),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = "Success",
            tint = NeonGreen,
            modifier = Modifier
                .size(100.dp)
                .scale(scale)
        )
    }
}




