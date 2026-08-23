package com.example.nfcgate.ui.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import compose.icons.FeatherIcons
import compose.icons.feathericons.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.nfcgate.theme.*
import com.example.nfcgate.ui.components.*
import com.example.nfcgate.ui.state.UiState
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboard() {
    var isRefreshing by remember { mutableStateOf(false) }
    var uiState by remember { mutableStateOf<UiState<List<Int>>>(UiState.Loading) }

    LaunchedEffect(Unit) {
        delay(1500)
        uiState = UiState.Success((1..5).toList())
    }

    fun onRefresh() {
        isRefreshing = true
        uiState = UiState.Loading
    }

    LaunchedEffect(isRefreshing) {
        if (isRefreshing) {
            delay(1000)
            uiState = UiState.Success((1..5).toList())
            isRefreshing = false
        }
    }

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = { onRefresh() },
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {
        Spacer(modifier = Modifier.height(24.dp))
        // Top Header
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(FeatherIcons.Menu, contentDescription = "Menu", tint = TextPrimary, modifier = Modifier.size(22.dp))
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text("Good morning, Amr 👋", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = TextPrimary)
                    Text("Here's what's happening today.", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                }
            }
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(SurfaceVariantColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(FeatherIcons.Bell, contentDescription = "Notifications", tint = TextPrimary, modifier = Modifier.size(18.dp))
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(PrimaryColor, shape = CircleShape)
                        .align(Alignment.TopEnd)
                )
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // Stats Grid
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            StatCard(
                title = "Users",
                value = "128",
                subtitle = "+12 today",
                icon = FeatherIcons.Users,
                badgeTint = PrimaryTint,
                iconTint = PrimaryColor,
                modifier = Modifier.weight(1f)
            )
            StatCard(
                title = "Devices",
                value = "56",
                subtitle = "+8 today",
                icon = FeatherIcons.Smartphone,
                badgeTint = PrimaryTint,
                iconTint = PrimaryColor,
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            StatCard(
                title = "Active Sessions",
                value = "12",
                subtitle = "Live now",
                icon = FeatherIcons.CheckCircle,
                badgeTint = SuccessTint,
                iconTint = SuccessColor,
                modifier = Modifier.weight(1f)
            )
            StatCard(
                title = "Revenue",
                value = "$4,320",
                subtitle = "+4.2%",
                icon = FeatherIcons.DollarSign,
                badgeTint = WarningTint,
                iconTint = WarningColor,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(28.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Recent Activity", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = TextPrimary)
            Text("View all", style = MaterialTheme.typography.bodyMedium, color = PrimaryColor, fontWeight = FontWeight.SemiBold)
        }

        Spacer(modifier = Modifier.height(12.dp))

        StatefulLayout(
            state = uiState,
            emptyIcon = FeatherIcons.Activity,
            emptyTitle = "No recent activity",
            emptySubtitle = "There is no recent activity to show.",
            emptyActionText = null,
            onEmptyAction = null,
            onErrorRetry = { uiState = UiState.Loading },
            modifier = Modifier.weight(1f)
        ) { data ->
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(bottom = 24.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(data.size) { index ->
                    AnimatedListItem(index = index) {
                        ActivityItem()
                    }
                }
            }
            }
        }
    }
}

@Composable
fun ActivityItem() {
    AppCard(padding = 14.dp) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconBadge(icon = FeatherIcons.User, tint = PrimaryTint, iconTint = PrimaryColor, size = 40.dp, shape = CircleShape)
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text("Ahmed Ali created a session", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                Spacer(modifier = Modifier.height(2.dp))
                Text("2 mins ago", style = MaterialTheme.typography.labelMedium, color = TextSecondary)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminUsersScreen() {
    var selectedTab by remember { mutableStateOf(0) }
    var uiState by remember { mutableStateOf<UiState<List<Int>>>(UiState.Loading) }
    var isRefreshing by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(1500) // Simulate network delay
        uiState = UiState.Success((1..10).toList())
    }

    fun onRefresh() {
        isRefreshing = true
        // Simulate network refresh
        uiState = UiState.Loading
        // Cannot use CoroutineScope directly without rememberCoroutineScope, let's use a LaunchedEffect triggered by isRefreshing.
    }

    LaunchedEffect(isRefreshing) {
        if (isRefreshing) {
            delay(1000)
            uiState = UiState.Success((1..10).toList())
            isRefreshing = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("Users", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = TextPrimary)
            IconBadge(
                icon = FeatherIcons.Plus,
                tint = PrimaryColor,
                iconTint = Color.White,
                size = 40.dp,
                iconSize = 20.dp,
                shape = RoundedCornerShape(12.dp),
                onClick = { /* TODO: Add user */ }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        AppTextField(
            value = "",
            onValueChange = {},
            label = "Search users...",
            leadingIcon = { Icon(FeatherIcons.Search, contentDescription = null, tint = TextHint) }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Tabs — pill-style, only the active one gets a filled background
        val tabs = listOf("All", "Active", "Pending", "Blocked")
        AppTabRow(tabs = tabs, selectedTabIndex = selectedTab, onTabSelected = { selectedTab = it })

        Spacer(modifier = Modifier.height(24.dp))

        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = { onRefresh() },
            modifier = Modifier.weight(1f)
        ) {
            StatefulLayout(
                state = uiState,
                emptyIcon = FeatherIcons.Users,
                emptyTitle = "No users yet",
                emptySubtitle = "There are currently no users in the system.",
                emptyActionText = "Add User",
                onEmptyAction = { /* TODO */ },
                onErrorRetry = { uiState = UiState.Loading },
                modifier = Modifier.fillMaxSize()
            ) { data ->
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(bottom = 24.dp)
                ) {
                    items(data.size) { index ->
                        AnimatedListItem(index = index) {
                            UserItemCard(
                                name = if (index % 2 == 0) "Ahmed Ali" else "Sara Mohamed",
                                email = if (index % 2 == 0) "ahmed@example.com" else "sara@example.com",
                                role = if (index == 0) "Admin" else "User",
                                status = if (index % 3 == 0) "Pending" else "Active",
                                statusColor = if (index % 3 == 0) WarningColor else SuccessColor
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun UserItemCard(name: String, email: String, role: String, status: String, statusColor: Color) {
    AppCard(padding = 14.dp) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconBadge(
                icon = FeatherIcons.User,
                tint = PrimaryTint,
                iconTint = PrimaryColor,
                size = 44.dp,
                shape = CircleShape
            )
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                Text(email, style = MaterialTheme.typography.bodySmall, color = TextSecondary)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(role, style = MaterialTheme.typography.labelMedium, color = TextSecondary)
                Spacer(modifier = Modifier.height(6.dp))
                StatusBadge(text = status, color = statusColor)
            }
        }
    }
}