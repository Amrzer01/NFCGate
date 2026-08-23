package com.example.nfcgate.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nfcgate.core.data.FirestoreManager
import com.example.nfcgate.core.security.KeyStoreManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val role: String) : AuthState()
    data class Error(val message: String) : AuthState()
}

class AuthViewModel(
    private val firestoreManager: FirestoreManager,
    private val keyStoreManager: KeyStoreManager
) : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    init {
        checkToken()
    }

    private fun checkToken() {
        viewModelScope.launch {
            if (auth.currentUser != null) {
                _authState.value = AuthState.Loading
                val role = firestoreManager.getUserRole()
                _authState.value = AuthState.Success(role)
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                // If it fails to sign in, try creating an account instead. (For simplicity in testing)
                try {
                    auth.signInWithEmailAndPassword(email, password).await()
                } catch (e: Exception) {
                    auth.createUserWithEmailAndPassword(email, password).await()
                }

                // 2. Register Device
                val pubKey = keyStoreManager.generateOrGetKeyPair()
                try {
                    val deviceId = "DEV_" + System.currentTimeMillis().toString().takeLast(6)
                    firestoreManager.registerDevice(
                        deviceId = deviceId,
                        deviceName = android.os.Build.MODEL ?: "Android Device",
                        deviceModel = android.os.Build.DEVICE ?: "Unknown",
                        publicKey = pubKey
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                val role = firestoreManager.getUserRole()
                _authState.value = AuthState.Success(role)
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Authentication failed")
            }
        }
    }

    fun logout() {
        auth.signOut()
        _authState.value = AuthState.Idle
    }
}
