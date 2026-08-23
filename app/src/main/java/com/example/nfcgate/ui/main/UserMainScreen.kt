package com.example.nfcgate.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import compose.icons.FeatherIcons
import compose.icons.feathericons.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nfcgate.theme.*
import com.example.nfcgate.ui.user.*
import com.example.nfcgate.ui.settings.SettingsScreen

@Composable
fun UserMainScreen(onStartSession: () -> Unit, onLogout: () -> Unit) {
    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf("Home", "Sessions", "Profile", "Settings")
    val icons = listOf(FeatherIcons.Home, FeatherIcons.List, FeatherIcons.User, FeatherIcons.Settings)

    Scaffold(
        containerColor = BackgroundColor,
        bottomBar = {
            NavigationBar(
                containerColor = SurfaceColor,
                contentColor = PrimaryColor,
                tonalElevation = 0.dp,
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .navigationBarsPadding()
            ) {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(icons[index], contentDescription = item) },
                        label = { Text(item) },
                        selected = selectedItem == index,
                        onClick = { selectedItem = index },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = PrimaryColor,
                            selectedTextColor = PrimaryColor,
                            unselectedIconColor = TextHint,
                            unselectedTextColor = TextHint,
                            indicatorColor = SurfaceVariantColor
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (selectedItem) {
                0 -> UserHomeScreen(onStartSession = onStartSession)
                1 -> MySessionsScreen()
                2 -> UserProfileScreen()
                3 -> SettingsScreen(onLogout = onLogout)
            }
        }
    }
}
