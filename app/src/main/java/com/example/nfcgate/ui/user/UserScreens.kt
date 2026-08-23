package com.example.nfcgate.ui.user

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import compose.icons.FeatherIcons
import compose.icons.feathericons.Bell
import compose.icons.feathericons.ChevronRight
import compose.icons.feathericons.Lock
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
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        
        // Top Header
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(FeatherIcons.Menu, contentDescription = "Menu", tint = TextPrimary, modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text("Good evening, Ahmed", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = TextPrimary)
                    Text("Your relay is standing by", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                }
            }
            IconBadge(
                icon = FeatherIcons.Bell,
                size = 44.dp,
                iconSize = 20.dp,
                tint = SurfaceColor,
                iconTint = TextPrimary,
                shape = CircleShape,
                contentDescription = "Notifications"
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Primary security status
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(28.dp),
            color = TextPrimary
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    IconBadge(
                        icon = FeatherIcons.Lock,
                        size = 48.dp,
                        iconSize = 22.dp,
                        tint = PrimaryColor,
                        iconTint = Color.White,
                        shape = RoundedCornerShape(16.dp)
                    )
                    com.example.nfcgate.ui.components.StatusBadge(
                        text = "PROTECTED",
                        color = SuccessColor,
                        tint = SuccessColor.copy(alpha = 0.16f)
                    )
                }
                Spacer(modifier = Modifier.height(28.dp))
                Text(
                    "Connection Status",
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.White.copy(alpha = 0.68f)
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    "Secure and ready",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Your device is authorized for private NFC relay sessions.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.68f)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // My Device Card
        com.example.nfcgate.ui.components.AppCard(padding = 16.dp, containerColor = SurfaceColor) {
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
        
        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            com.example.nfcgate.ui.components.AppCard(
                modifier = Modifier.weight(1f),
                padding = 16.dp,
                containerColor = SurfaceColor
            ) {
                Text("Sessions", style = MaterialTheme.typography.labelMedium, color = TextSecondary)
                Spacer(modifier = Modifier.height(6.dp))
                Text("12", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = TextPrimary)
                Text("This month", style = MaterialTheme.typography.labelSmall, color = TextHint)
            }
            com.example.nfcgate.ui.components.AppCard(
                modifier = Modifier.weight(1f),
                padding = 16.dp,
                containerColor = SurfaceColor
            ) {
                Text("Avg. latency", style = MaterialTheme.typography.labelMedium, color = TextSecondary)
                Spacer(modifier = Modifier.height(6.dp))
                Text("38ms", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = TextPrimary)
                Text("Excellent", style = MaterialTheme.typography.labelSmall, color = SuccessColor)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        
        PrimaryButton(text = "Start Session", onClick = onStartSession)
        Spacer(modifier = Modifier.height(20.dp))
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
