package com.example.nfcgate.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nfcgate.theme.*
import com.example.nfcgate.ui.state.UiState
import compose.icons.FeatherIcons
import compose.icons.feathericons.AlertTriangle

@Composable
fun <T> StatefulLayout(
    state: UiState<T>,
    emptyIcon: ImageVector,
    emptyTitle: String,
    emptySubtitle: String,
    emptyActionText: String? = null,
    onEmptyAction: (() -> Unit)? = null,
    onErrorRetry: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable (T) -> Unit
) {
    when (state) {
        is UiState.Loading -> {
            LoadingPlaceholder(modifier)
        }
        is UiState.Empty -> {
            EmptyStateView(
                icon = emptyIcon,
                title = emptyTitle,
                subtitle = emptySubtitle,
                actionText = emptyActionText,
                onAction = onEmptyAction,
                modifier = modifier
            )
        }
        is UiState.Error -> {
            ErrorStateView(
                message = state.message,
                onRetry = onErrorRetry,
                modifier = modifier
            )
        }
        is UiState.Success -> {
            content(state.data)
        }
    }
}

@Composable
fun LoadingPlaceholder(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "shimmer")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 0.85f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "shimmer_alpha"
    )

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        repeat(4) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(76.dp)
                    .alpha(alpha)
                    .clip(RoundedCornerShape(18.dp))
                    .background(SurfaceVariantColor)
            )
        }
    }
}

@Composable
fun EmptyStateView(
    icon: ImageVector,
    title: String,
    subtitle: String,
    actionText: String?,
    onAction: (() -> Unit)?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp, vertical = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        IconBadge(
            icon = icon,
            size = 68.dp,
            iconSize = 28.dp,
            tint = SurfaceVariantColor,
            iconTint = TextSecondary,
            shape = RoundedCornerShape(20.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = subtitle,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            color = TextSecondary,
            textAlign = TextAlign.Center
        )

        if (actionText != null && onAction != null) {
            Spacer(modifier = Modifier.height(28.dp))
            PrimaryButton(
                text = actionText,
                onClick = onAction,
                modifier = Modifier.widthIn(max = 220.dp)
            )
        }
    }
}

@Composable
fun ErrorStateView(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp, vertical = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        IconBadge(
            icon = FeatherIcons.AlertTriangle,
            size = 68.dp,
            iconSize = 28.dp,
            tint = ErrorTint,
            iconTint = ErrorColor,
            shape = RoundedCornerShape(20.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Service Interrupted",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = message,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            color = TextSecondary,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(28.dp))
        OutlineButton(
            text = "Retry",
            onClick = onRetry,
            modifier = Modifier.widthIn(max = 180.dp),
            color = PrimaryColor
        )
    }
}