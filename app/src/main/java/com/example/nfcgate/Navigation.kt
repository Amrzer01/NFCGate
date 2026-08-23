package com.example.nfcgate

import androidx.compose.runtime.Composable
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.nfcgate.ui.auth.SplashScreen
import com.example.nfcgate.ui.auth.LoginScreen
import com.example.nfcgate.ui.admin.AdminDashboard
import com.example.nfcgate.ui.user.UserHomeScreen
import com.example.nfcgate.ui.user.ActiveSessionScreen

@Composable
fun MainNavigation() {
  val backStack = rememberNavBackStack(Splash)
  val appContainer = com.example.nfcgate.core.di.LocalAppContainer.current

  NavDisplay(
    backStack = backStack,
    transitionSpec = {
        slideIntoContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.Left,
            animationSpec = tween(300, easing = FastOutSlowInEasing)
        ) + fadeIn(animationSpec = tween(300)) togetherWith
        slideOutOfContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.Left,
            animationSpec = tween(300, easing = FastOutSlowInEasing)
        ) + fadeOut(animationSpec = tween(300))
    },
    popTransitionSpec = {
        slideIntoContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.Right,
            animationSpec = tween(300, easing = FastOutSlowInEasing)
        ) + fadeIn(animationSpec = tween(300)) togetherWith
        slideOutOfContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.Right,
            animationSpec = tween(300, easing = FastOutSlowInEasing)
        ) + fadeOut(animationSpec = tween(300))
    },
    onBack = { backStack.removeLastOrNull() },
    entryProvider =
      entryProvider {
        entry<Splash> {
          val authViewModel = androidx.lifecycle.viewmodel.compose.viewModel<com.example.nfcgate.ui.auth.AuthViewModel>(
              factory = object : androidx.lifecycle.ViewModelProvider.Factory {
                  override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                      return com.example.nfcgate.ui.auth.AuthViewModel(
                          appContainer.firestoreManager,
                          appContainer.keyStoreManager
                      ) as T
                  }
              }
          )
          
          val authState by authViewModel.authState.collectAsState()
          LaunchedEffect(authState) {
              if (authState is com.example.nfcgate.ui.auth.AuthState.Success) {
                  val role = (authState as com.example.nfcgate.ui.auth.AuthState.Success).role
                  backStack.removeLastOrNull()
                  if (role == "ADMIN") backStack.add(AdminHome) else backStack.add(UserHome)
              }
          }

          SplashScreen(onNavigateToLogin = { 
            backStack.removeLastOrNull()
            backStack.add(Login) 
          })
        }
        entry<Login> {
          val authViewModel = androidx.lifecycle.viewmodel.compose.viewModel<com.example.nfcgate.ui.auth.AuthViewModel>(
              factory = object : androidx.lifecycle.ViewModelProvider.Factory {
                  override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                      return com.example.nfcgate.ui.auth.AuthViewModel(
                          appContainer.firestoreManager,
                          appContainer.keyStoreManager
                      ) as T
                  }
              }
          )
          
          val authState by authViewModel.authState.collectAsState()
          
          LaunchedEffect(authState) {
              if (authState is com.example.nfcgate.ui.auth.AuthState.Success) {
                  val role = (authState as com.example.nfcgate.ui.auth.AuthState.Success).role
                  backStack.removeLastOrNull()
                  if (role == "ADMIN") backStack.add(AdminHome) else backStack.add(UserHome)
              }
          }

          // We pass the viewmodel to the LoginScreen to perform the actual login action
          LoginScreen(
              onLoginClick = { email, password ->
                  authViewModel.login(email, password)
              },
              onLoginSuccess = { role ->
                 // No longer used directly, but kept for compatibility if needed.
              },
              isLoading = authState is com.example.nfcgate.ui.auth.AuthState.Loading,
              error = (authState as? com.example.nfcgate.ui.auth.AuthState.Error)?.message
          )
        }
        entry<AdminHome> {
          com.example.nfcgate.ui.main.AdminMainScreen(onLogout = { 
              com.google.firebase.auth.FirebaseAuth.getInstance().signOut()
              backStack.removeLastOrNull()
              backStack.add(Login) 
          })
        }
        entry<UserHome> {
          com.example.nfcgate.ui.main.UserMainScreen(
              onStartSession = { backStack.add(UserSessionActive) },
              onLogout = { 
                  com.google.firebase.auth.FirebaseAuth.getInstance().signOut()
                  backStack.removeLastOrNull()
                  backStack.add(Login) 
              }
          )
        }
        entry<UserSessionActive> {
          ActiveSessionScreen(onEndSession = { backStack.removeLastOrNull() })
        }
      },
  )
}
