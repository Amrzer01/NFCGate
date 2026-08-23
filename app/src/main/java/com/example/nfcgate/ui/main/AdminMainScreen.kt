package com.example.nfcgate.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nfcgate.theme.*
import com.example.nfcgate.ui.admin.*
import com.example.nfcgate.ui.settings.SettingsScreen
import com.example.nfcgate.ui.user.MySessionsScreen
import compose.icons.FeatherIcons
import compose.icons.feathericons.*

@Composable
fun AdminMainScreen(onLogout: () -> Unit) {
    var selectedItem by remember { mutableStateOf(0) }
    var selectedDeviceId by remember { mutableStateOf<String?>(null) }
    val items = listOf("Dashboard", "Users", "Devices", "Sessions", "More")
    val icons = listOf(
        FeatherIcons.Grid,
        FeatherIcons.Users,
        FeatherIcons.Smartphone,
        FeatherIcons.Activity,
        FeatherIcons.MoreHorizontal
    )

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
                    val isSelected = selectedItem == index
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = icons[index],
                                contentDescription = item,
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        label = {
                            Text(
                                text = item,
                                fontSize = 11.sp
                            )
                        },
                        selected = isSelected,
                        onClick = {
                            selectedItem = index
                            if (index != 2) selectedDeviceId = null
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = PrimaryColor,
                            selectedTextColor = PrimaryColor,
                            unselectedIconColor = TextHint,
                            unselectedTextColor = TextHint,
                            indicatorColor = PrimaryTint
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (selectedItem) {
                0 -> AdminDashboard()
                1 -> AdminUsersScreen()
                2 -> {
                    if (selectedDeviceId != null) {
                        DeviceDetailsScreen(
                            deviceId = selectedDeviceId!!,
                            onBack = { selectedDeviceId = null }
                        )
                    } else {
                        DevicesManagementScreen(onDeviceSelected = { selectedDeviceId = it })
                    }
                }
                3 -> MySessionsScreen()
                4 -> SettingsScreen(onLogout = onLogout)
            }
        }
    }
}