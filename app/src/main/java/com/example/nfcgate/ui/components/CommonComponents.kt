package com.example.nfcgate.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nfcgate.theme.*
import kotlinx.coroutines.delay

@Composable
fun StatusBadge(text: String, color: Color, tint: Color = color.copy(alpha = 0.10f)) {
    val animatedColor by animateColorAsState(targetValue = color, animationSpec = tween(300), label = "statusColor")
    val animatedTint by animateColorAsState(targetValue = tint, animationSpec = tween(300), label = "statusTint")

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(animatedTint)
            .padding(horizontal = 10.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = animatedColor,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp
        )
    }
}

@Composable
fun IconBadge(
    icon: ImageVector,
    modifier: Modifier = Modifier,
    size: Dp = 44.dp,
    iconSize: Dp = 20.dp,
    tint: Color = PrimaryTint,
    iconTint: Color = PrimaryColor,
    shape: Shape = CircleShape,
    onClick: (() -> Unit)? = null,
    contentDescription: String? = null
) {
    val boxModifier = modifier
        .size(size)
        .clip(shape)
        .background(tint)

    Box(
        modifier = if (onClick != null) boxModifier.clickable(onClick = onClick) else boxModifier,
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = iconTint,
            modifier = Modifier.size(iconSize)
        )
    }
}

@Composable
fun Dot(color: Color, size: Dp = 8.dp) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(color)
    )
}

@Composable
fun AnimatedPulse(modifier: Modifier = Modifier, color: Color = PrimaryColor) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 2.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "pulse_scale"
    )
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "pulse_alpha"
    )

    Box(
        modifier = modifier
            .size(48.dp)
            .scale(scale)
            .clip(CircleShape)
            .background(color.copy(alpha = alpha))
    )
}

@Composable
fun HeartbeatEcgLine(modifier: Modifier = Modifier, color: Color = PrimaryColor) {
    val infiniteTransition = rememberInfiniteTransition(label = "ecg")
    val phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "ecgPhase"
    )

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
    ) {
        val width = size.width
        val height = size.height
        val midY = height / 2f

        val path = Path().apply {
            moveTo(0f, midY)
            var x = -phase % 300f
            while (x < width + 300f) {
                lineTo(x + 40f, midY)
                lineTo(x + 55f, midY - 15f)
                lineTo(x + 70f, midY + 10f)
                lineTo(x + 85f, midY)
                lineTo(x + 100f, midY - 70f)
                lineTo(x + 115f, midY + 50f)
                lineTo(x + 130f, midY)
                lineTo(x + 160f, midY - 20f)
                lineTo(x + 190f, midY)
                lineTo(x + 300f, midY)
                x += 300f
            }
        }

        drawPath(
            path = path,
            color = color,
            style = Stroke(width = 4f)
        )
    }
}

@Composable
fun AnimatedListItem(
    index: Int,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(index * 35L)
        visible = true
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(300)) +
                slideInVertically(
                    initialOffsetY = { 30 },
                    animationSpec = tween(300, easing = FastOutSlowInEasing)
                ),
        modifier = modifier
    ) {
        content()
    }
}