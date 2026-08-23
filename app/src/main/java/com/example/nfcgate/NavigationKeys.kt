package com.example.nfcgate

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable data object Splash : NavKey
@Serializable data object Login : NavKey

// Admin flow
@Serializable data object AdminHome : NavKey
@Serializable data object AdminUsers : NavKey
@Serializable data object AdminDevices : NavKey

// User flow
@Serializable data object UserHome : NavKey
@Serializable data object UserSessionActive : NavKey
