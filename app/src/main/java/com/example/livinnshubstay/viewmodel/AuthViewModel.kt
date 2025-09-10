package com.example.livinnshubstay.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.livinnshubstay.data.AuthEvent
import com.example.livinnshubstay.data.AuthState
import com.example.livinnshubstay.data.EmergencyContact
import com.example.livinnshubstay.data.Guardian
import com.example.livinnshubstay.data.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class AuthViewModel : ViewModel() {
    
    private val _authState = MutableStateFlow(AuthState())
    val authState: StateFlow<AuthState> = _authState.asStateFlow()
    
    // Mock credentials for demo
    private val DEMO_EMAIL = "test@demo.com"
    private val DEMO_PASSWORD = "123456"
    
    // Additional dummy credentials
    private val DUMMY_EMAIL = "demo"
    private val DUMMY_PASSWORD = "pass"
    
    fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.Login -> {
                login(event.email, event.password)
            }
            is AuthEvent.Register -> {
                register(event)
            }
            is AuthEvent.Logout -> {
                logout()
            }
            is AuthEvent.ClearError -> {
                _authState.value = _authState.value.copy(errorMessage = "")
            }
        }
    }
    
    private fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = _authState.value.copy(
                isLoading = true,
                errorMessage = ""
            )
            
            // Simulate network delay
            delay(1500)
            
            // Check both sets of credentials
            if ((email == DEMO_EMAIL && password == DEMO_PASSWORD) || 
                (email == DUMMY_EMAIL && password == DUMMY_PASSWORD)) {
                
                // Successful login
                val user = User(
                    id = "user_${UUID.randomUUID()}",
                    email = email,
                    name = if (email == DEMO_EMAIL) "John Doe" else "Demo User",
                    profileImage = ""
                )
                
                _authState.value = _authState.value.copy(
                    isLoading = false,
                    user = user,
                    isAuthenticated = true,
                    errorMessage = ""
                )
            } else {
                // Failed login
                _authState.value = _authState.value.copy(
                    isLoading = false,
                    errorMessage = "Invalid email or password. Try $DUMMY_EMAIL / $DUMMY_PASSWORD",
                    isAuthenticated = false
                )
            }
        }
    }
    
    private fun register(event: AuthEvent.Register) {
        viewModelScope.launch {
            _authState.value = _authState.value.copy(
                isLoading = true,
                errorMessage = ""
            )
            
            // Simulate network delay
            delay(2000)
            
            // Create new user
            val newUser = User(
                id = "user_${UUID.randomUUID()}",
                email = event.email,
                name = event.name,
                phoneNumber = event.phoneNumber,
                address = event.address,
                aadharNumber = event.aadharNumber,
                emergencyContact = event.emergencyContact,
                guardian = event.guardian
            )
            
            // In a real app, you would save this user to a database
            // For now, we'll just update the state
            _authState.value = _authState.value.copy(
                isLoading = false,
                user = newUser,
                isAuthenticated = true,
                errorMessage = ""
            )
        }
    }
    
    private fun logout() {
        _authState.value = AuthState()
    }
    
    fun resetAuthState() {
        _authState.value = AuthState()
    }
    
    // Helper function to register a user with all details
    fun registerUser(
        email: String,
        password: String,
        name: String,
        phoneNumber: String,
        address: String,
        aadharNumber: String,
        emergencyContactName: String,
        emergencyContactPhone: String,
        guardianName: String,
        guardianPhone: String
    ) {
        val event = AuthEvent.Register(
            email = email,
            password = password,
            name = name,
            phoneNumber = phoneNumber,
            address = address,
            aadharNumber = aadharNumber,
            emergencyContact = EmergencyContact(
                name = emergencyContactName,
                phoneNumber = emergencyContactPhone
            ),
            guardian = Guardian(
                name = guardianName,
                phoneNumber = guardianPhone
            )
        )
        
        onEvent(event)
    }
}
