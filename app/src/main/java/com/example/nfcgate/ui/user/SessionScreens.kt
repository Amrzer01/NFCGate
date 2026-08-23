package com.example.nfcgate.ui.user

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nfcgate.theme.*
import com.example.nfcgate.ui.components.StatusBadge
import com.example.nfcgate.ui.components.AppTabRow
import com.example.nfcgate.ui.components.NfcWavesCanvas
import compose.icons.FeatherIcons
import compose.icons.feathericons.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MySessionsScreen() {
    var selectedTab by remember { mutableStateOf(0) }
    var uiState by remember { mutableStateOf<com.example.nfcgate.ui.state.UiState<List<SessionInfo>>>(com.example.nfcgate.ui.state.UiState.Loading) }
    var isRefreshing by remember { mutableStateOf(false) }

    val tabs = listOf("All", "Active", "Completed")

    val mockData = listOf(
        SessionInfo("#SES-8X7Y", "2 mins ago", "Active", SuccessColor, "38ms", "1,284"),
        SessionInfo("#SES-1A2B", "2 hours ago", "Completed", TextHint, "45ms", "5,120"),
        SessionInfo("#SES-9C8D", "Yesterday", "Completed", TextHint, "42ms", "8,901")
    )

    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(1500)
        uiState = com.example.nfcgate.ui.state.UiState.Success(mockData)
    }

    fun onRefresh() {
        isRefreshing = true
        uiState = com.example.nfcgate.ui.state.UiState.Loading
    }

    LaunchedEffect(isRefreshing) {
        if (isRefreshing) {
            kotlinx.coroutines.delay(1000)
            uiState = com.example.nfcgate.ui.state.UiState.Success(mockData)
            isRefreshing = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .statusBarsPadding()
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Text("My Sessions", style = MaterialTheme.typography.headlineLarge.copy(fontSize = 32.sp), fontWeight = FontWeight.Bold, color = TextPrimary)
        Spacer(modifier = Modifier.height(24.dp))

        // Tabs
        AppTabRow(tabs = tabs, selectedTabIndex = selectedTab, onTabSelected = { selectedTab = it })

        Spacer(modifier = Modifier.height(24.dp))

        // Mock Data handled in LaunchedEffect

        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = { onRefresh() },
            modifier = Modifier.weight(1f)
        ) {
            com.example.nfcgate.ui.components.StatefulLayout(
                state = uiState,
                emptyIcon = FeatherIcons.List,
                emptyTitle = "No sessions found",
                emptySubtitle = "You haven't participated in any NFC sessions yet.",
                emptyActionText = "Start Session",
                onEmptyAction = { /* TODO */ },
                onErrorRetry = { uiState = com.example.nfcgate.ui.state.UiState.Loading },
                modifier = Modifier.fillMaxSize()
            ) { data ->
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(bottom = 24.dp)
                ) {
                    itemsIndexed(data) { index, session ->
                        com.example.nfcgate.ui.components.AnimatedListItem(index = index) {
                            SessionCard(session)
                        }
                    }
                }
            }
        }
    }
}

data class SessionInfo(val id: String, val time: String, val status: String, val statusColor: androidx.compose.ui.graphics.Color, val latency: String, val packets: String)

@Composable
fun SessionCard(session: SessionInfo) {
    com.example.nfcgate.ui.components.AppCard(padding = 20.dp) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text(session.id, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, color = TextPrimary)
            StatusBadge(text = session.status, color = session.statusColor)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(session.time, style = MaterialTheme.typography.bodySmall, color = TextSecondary)
        
        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider(color = SurfaceVariantColor)
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column {
                Text("Latency", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                Text(session.latency, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold, color = TextPrimary)
            }
            Column {
                Text("Packets", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                Text(session.packets, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold, color = TextPrimary)
            }
            Text("Details", style = MaterialTheme.typography.bodyMedium, color = PrimaryColor, fontWeight = FontWeight.SemiBold)
        }
    }
}
