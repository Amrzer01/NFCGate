package com.example.nfcgate.ui.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import compose.icons.FeatherIcons
import compose.icons.feathericons.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nfcgate.theme.*
import com.example.nfcgate.ui.components.*

@Composable
fun UserProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Text("Profile", style = MaterialTheme.typography.headlineLarge.copy(fontSize = 32.sp), fontWeight = FontWeight.Bold, color = TextPrimary)
        Spacer(modifier = Modifier.height(28.dp))

        // Profile Header
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(PrimaryColor, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text("AA", style = MaterialTheme.typography.headlineLarge, color = Color.White, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text("Ahmed Ali", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold, color = TextPrimary)
            Spacer(modifier = Modifier.height(4.dp))
            StatusBadge(text = "User", color = PrimaryColor)
        }

        Spacer(modifier = Modifier.height(28.dp))

        AppCard(padding = 20.dp) {
            ProfileDetailRow("Email", "ahmed@example.com")
            HorizontalDivider(modifier = Modifier.padding(vertical = 14.dp), color = SurfaceVariantColor)
            ProfileDetailRow("Phone", "+1 234 567 890")
            HorizontalDivider(modifier = Modifier.padding(vertical = 14.dp), color = SurfaceVariantColor)
            ProfileDetailRow("Joined", "March 2024")
        }

        Spacer(modifier = Modifier.height(28.dp))
        
        Text("Recent Activity", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = TextPrimary)
        Spacer(modifier = Modifier.height(16.dp))

        AppCard(padding = 16.dp) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconBadge(icon = FeatherIcons.CheckCircle, tint = SuccessTint, iconTint = SuccessColor, size = 40.dp, shape = CircleShape)
                Spacer(modifier = Modifier.width(14.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text("Successful session #SES-8X7Y", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                    Spacer(modifier = Modifier.height(2.dp))
                    Text("Today, 10:45 AM", style = MaterialTheme.typography.labelMedium, color = TextSecondary)
                }
            }
        }
    }
}

@Composable
fun ProfileDetailRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
        Text(value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold, color = TextPrimary)
    }
}
