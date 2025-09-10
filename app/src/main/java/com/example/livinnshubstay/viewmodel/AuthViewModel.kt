package com.example.livinnshubstay.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.livinnshubstay.data.AuthEvent
import com.example.livinnshubstay.data.AuthState
import com.example.livinnshubstay.data.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    
    private val _authState = MutableStateFlow(AuthState())
    val authState: StateFlow<AuthState> = _authState.asStateFlow()
    
    // Mock credentials for demo
    private val DEMO_EMAIL = "test@demo.com"
    private val DEMO_PASSWORD = "123456"
    
    fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.Login -> {
                login(event.email, event.password)
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
            
            if (email == DEMO_EMAIL && password == DEMO_PASSWORD) {
                // Successful login
                val user = User(
                    id = "demo_user_123",
                    email = email,
                    name = "John Doe",
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
                    errorMessage = "Invalid email or password. Use test@demo.com / 123456",
                    isAuthenticated = false
                )
            }
        }
    }
    
    private fun logout() {
        _authState.value = AuthState()
    }
    
    fun resetAuthState() {
        _authState.value = AuthState()
    }
}
