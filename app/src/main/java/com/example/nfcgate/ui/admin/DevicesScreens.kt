package com.example.nfcgate.ui.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nfcgate.theme.*
import com.example.nfcgate.ui.components.*
import com.example.nfcgate.ui.state.UiState
import compose.icons.FeatherIcons
import compose.icons.feathericons.*
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DevicesManagementScreen(onDeviceSelected: (String) -> Unit) {
    var uiState by remember { mutableStateOf<UiState<List<Int>>>(UiState.Loading) }
    var isRefreshing by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(1500)
        uiState = UiState.Success((1..6).toList())
    }

    fun onRefresh() {
        isRefreshing = true
        uiState = UiState.Loading
    }

    LaunchedEffect(isRefreshing) {
        if (isRefreshing) {
            delay(1000)
            uiState = UiState.Success((1..6).toList())
            isRefreshing = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .statusBarsPadding()
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(28.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Devices",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
                letterSpacing = (-0.5).sp
            )

            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(PrimaryColor)
                    .clickable { /* Add device action */ }
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = FeatherIcons.Plus,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(15.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Add Device",
                    fontSize = 13.sp,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Security Notice Card
        AppCard(
            padding = 16.dp,
            containerColor = WarningTint.copy(alpha = 0.4f)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconBadge(
                    icon = FeatherIcons.AlertCircle,
                    tint = WarningTint,
                    iconTint = WarningColor,
                    size = 36.dp,
                    iconSize = 18.dp,
                    shape = RoundedCornerShape(10.dp)
                )
                Spacer(modifier = Modifier.width(14.dp))
                Text(
                    text = "2 devices require OS updates to maintain cryptographic integrity.",
                    fontSize = 13.sp,
                    lineHeight = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextPrimary
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = { onRefresh() },
            modifier = Modifier.weight(1f)
        ) {
            StatefulLayout(
                state = uiState,
                emptyIcon = FeatherIcons.Smartphone,
                emptyTitle = "No devices yet",
                emptySubtitle = "Enroll your first hardware device to initiate relay sessions.",
                emptyActionText = "Add Device",
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
                            DeviceItemCard(
                                deviceName = if (index % 2 == 0) "iPhone 14 Pro" else "Galaxy S23",
                                owner = "Ahmed Ali",
                                os = if (index % 2 == 0) "iOS 17.2" else "Android 14",
                                status = if (index == 1) "Needs Update" else "Approved",
                                statusColor = if (index == 1) WarningColor else SuccessColor,
                                onClick = { onDeviceSelected("DEV-$index") }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DeviceItemCard(
    deviceName: String,
    owner: String,
    os: String,
    status: String,
    statusColor: Color,
    onClick: () -> Unit
) {
    AppCard(onClick = onClick, padding = 16.dp) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconBadge(
                icon = FeatherIcons.Smartphone,
                tint = PrimaryTint,
                iconTint = PrimaryColor,
                size = 44.dp,
                iconSize = 20.dp,
                shape = RoundedCornerShape(12.dp)
            )
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = deviceName,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "$owner • $os",
                    fontSize = 13.sp,
                    color = TextSecondary
                )
            }
            StatusBadge(text = status, color = statusColor)
        }
    }
}

@Composable
fun DeviceDetailsScreen(
    deviceId: String,
    onBack: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .systemBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(SurfaceColor)
                    .clickable { onBack() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = FeatherIcons.ChevronLeft,
                    contentDescription = "Back",
                    tint = TextPrimary,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Device Details",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
                letterSpacing = (-0.3).sp
            )
        }

        Spacer(modifier = Modifier.height(36.dp))

        // Hero Device Icon
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconBadge(
                icon = FeatherIcons.Smartphone,
                tint = PrimaryTint,
                iconTint = PrimaryColor,
                size = 80.dp,
                iconSize = 36.dp,
                shape = CircleShape
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "iPhone 14 Pro",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            Spacer(modifier = Modifier.height(8.dp))
            StatusBadge(text = "Approved", color = SuccessColor)
        }

        Spacer(modifier = Modifier.height(36.dp))

        AppCard(padding = 20.dp) {
            DetailRow("Device ID", deviceId)
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 14.dp),
                color = SurfaceVariantColor.copy(alpha = 0.6f)
            )
            DetailRow("Owner", "Ahmed Ali")
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 14.dp),
                color = OutlineColor
            )
            DetailRow("OS Version", "iOS 17.2")
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 14.dp),
                color = OutlineColor
            )
            DetailRow("Last Sync", "Today, 10:45 AM")
        }

        Spacer(modifier = Modifier.weight(1f))

        PrimaryButton(
            text = "Edit Configuration",
            onClick = { /* TODO */ }
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlineButton(
            text = "Revoke Access",
            onClick = { /* TODO */ },
            color = ErrorColor
        )
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = TextSecondary
        )
        Text(
            text = value,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextPrimary
        )
    }
}