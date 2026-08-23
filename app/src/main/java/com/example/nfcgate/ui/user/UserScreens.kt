package com.example.nfcgate.ui.user

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import compose.icons.FeatherIcons
import compose.icons.feathericons.Bell
import compose.icons.feathericons.ChevronRight
import compose.icons.feathericons.Menu
import compose.icons.feathericons.Smartphone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nfcgate.theme.*
import com.example.nfcgate.ui.components.HeartbeatEcgLine
import com.example.nfcgate.ui.components.HeartbeatEcgLine
import com.example.nfcgate.ui.components.PrimaryButton
import com.example.nfcgate.ui.components.IconBadge

@Composable
fun UserHomeScreen(onStartSession: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        
        // Top Header
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(FeatherIcons.Menu, contentDescription = "Menu", tint = TextPrimary, modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text("Hello, Ahmed 👋", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = TextPrimary)
                    Text("Ready to connect?", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                }
            }
            Icon(FeatherIcons.Bell, contentDescription = "Notifications", tint = TextPrimary, modifier = Modifier.size(24.dp))
        }
        
        Spacer(modifier = Modifier.height(28.dp))
        
        // Connection Status Card
        com.example.nfcgate.ui.components.AppCard(padding = 24.dp) {
            Text("Connection Status", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold, color = TextPrimary)
            Spacer(modifier = Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                com.example.nfcgate.ui.components.StatusBadge(text = "Connected", color = SuccessColor)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text("Your device is authorized", style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // My Device Card
        com.example.nfcgate.ui.components.AppCard(padding = 16.dp) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
            IconBadge(
                icon = FeatherIcons.Smartphone,
                tint = SurfaceColor,
                iconTint = TextPrimary,
                size = 48.dp,
                iconSize = 24.dp,
                shape = RoundedCornerShape(12.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text("My Device", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                Spacer(modifier = Modifier.height(4.dp))
                Text("iPhone 14 Pro", style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
            }
            Text("iOS 17.2", style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
            Spacer(modifier = Modifier.width(8.dp))
            Icon(imageVector = FeatherIcons.ChevronRight, contentDescription = null, tint = TextHint)
            }
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        PrimaryButton(text = "Start Session", onClick = onStartSession)
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun ActiveSessionScreen(onEndSession: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        
        Text("Session in Progress", style = MaterialTheme.typography.headlineMedium, color = TextPrimary, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            com.example.nfcgate.ui.components.StatusBadge(text = "00:02:45", color = SuccessColor)
        }
        
        Spacer(modifier = Modifier.height(28.dp))
        
        // Visual Relay
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Reader
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier.size(80.dp).background(SurfaceVariantColor, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    com.example.nfcgate.ui.components.NfcWavesCanvas(
                        modifier = Modifier.fillMaxSize(),
                        color = PrimaryColor
                    )
                    Box(modifier = Modifier.size(24.dp).background(TextPrimary, CircleShape))
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text("Reader", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                Text("This Device", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
            }
            
            // Heartbeat Line
            HeartbeatEcgLine(modifier = Modifier.weight(1f).padding(horizontal = 16.dp))
            
            // Emulator
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier.size(80.dp).background(SurfaceVariantColor, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    com.example.nfcgate.ui.components.NfcWavesCanvas(
                        modifier = Modifier.fillMaxSize(),
                        color = PrimaryColor
                    )
                    Box(modifier = Modifier.size(24.dp).background(TextPrimary, CircleShape))
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text("Emulator", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                Text("Remote Device", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
            }
        }
        
        Spacer(modifier = Modifier.height(28.dp))
        
        // Info Card
        com.example.nfcgate.ui.components.AppCard(padding = 24.dp) {
            Text("Session ID", style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
            Text("#SES-8X7Y", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Server", style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
                Text("nfcgate.yourserver.com", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
            }
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = SurfaceVariantColor)
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text("Latency", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                    Text("38ms", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                }
                Column {
                    Text("Packets", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                    Text("1,284", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                }
                Column {
                    Text("Status", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                    Text("Secure Relay", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                }
            }
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        com.example.nfcgate.ui.components.OutlineButton(
            text = "End Session",
            onClick = onEndSession,
            color = ErrorColor
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}
