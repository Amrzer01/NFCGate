package com.example.nfcgate.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import compose.icons.FeatherIcons
import compose.icons.feathericons.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nfcgate.theme.*
import com.example.nfcgate.ui.components.AppCard
import com.example.nfcgate.ui.components.IconBadge
import com.example.nfcgate.ui.components.OutlineButton

@Composable
fun SettingsScreen(onLogout: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Text("Settings", style = MaterialTheme.typography.headlineLarge.copy(fontSize = 32.sp), fontWeight = FontWeight.Bold, color = TextPrimary)
        Spacer(modifier = Modifier.height(28.dp))

        // Profile Section
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(PrimaryColor, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text("AA", style = MaterialTheme.typography.headlineMedium, color = Color.White, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text("Ahmed Ali", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                Spacer(modifier = Modifier.height(2.dp))
                Text("ahmed@example.com", style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // Settings List
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                Text(
                    "ACCOUNT",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = TextHint,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
            item {
                SettingsItemRow(title = "Profile Information", icon = FeatherIcons.User, onClick = { /* TODO: navigate to profile */ })
            }
            item {
                SettingsItemRow(title = "Security & Privacy", icon = FeatherIcons.Lock, onClick = { /* TODO: navigate to security */ })
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "PREFERENCES",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = TextHint,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
            item {
                SettingsItemRow(title = "Notifications", icon = FeatherIcons.Bell, onClick = { /* TODO: navigate to notifications */ })
            }
            item {
                SettingsItemRow(title = "About NFCGate", icon = FeatherIcons.Info, onClick = { /* TODO: navigate to about */ })
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlineButton(
            text = "Logout",
            onClick = onLogout,
            color = ErrorColor,
            modifier = Modifier.padding(bottom = 24.dp)
        )
    }
}

@Composable
fun SettingsItemRow(title: String, icon: ImageVector, onClick: () -> Unit = {}) {
    AppCard(
        onClick = onClick,
        padding = 16.dp
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconBadge(
                icon = icon,
                tint = PrimaryTint,
                iconTint = PrimaryColor,
                size = 40.dp,
                shape = RoundedCornerShape(10.dp)
            )
            Spacer(modifier = Modifier.width(14.dp))
            Text(
                title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary,
                modifier = Modifier.weight(1f)
            )
            Icon(FeatherIcons.ChevronRight, contentDescription = null, tint = TextHint)
        }
    }
}