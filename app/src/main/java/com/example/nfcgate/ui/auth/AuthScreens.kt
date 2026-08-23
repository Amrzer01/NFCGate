package com.example.nfcgate.ui.auth

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.icons.FeatherIcons
import compose.icons.feathericons.Award
import compose.icons.feathericons.Check
import compose.icons.feathericons.Eye
import compose.icons.feathericons.EyeOff
import compose.icons.feathericons.Globe
import compose.icons.feathericons.Info
import compose.icons.feathericons.Lock
import compose.icons.feathericons.Smartphone
import compose.icons.feathericons.User
import compose.icons.feathericons.Zap
import com.example.nfcgate.theme.*
import com.example.nfcgate.ui.components.AppTextField
import com.example.nfcgate.ui.components.IconBadge
import com.example.nfcgate.ui.components.PrimaryButton
import com.example.nfcgate.ui.components.appleClickable

// ============================================================
// SPLASH SCREEN
// ============================================================

@Composable
fun SplashScreen(
    onNavigateToLogin: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .systemBarsPadding()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(24.dp))

        // ----------------------------------------------------
        // Top Brand
        // ----------------------------------------------------

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconBadge(
                icon = FeatherIcons.Zap,
                tint = PrimaryTint,
                iconTint = PrimaryColor,
                size = 42.dp,
                iconSize = 20.dp,
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = "NFCGate",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // ----------------------------------------------------
        // Main Logo
        // ----------------------------------------------------

        IconBadge(
            icon = FeatherIcons.Zap,
            tint = PrimaryTint,
            iconTint = PrimaryColor,
            size = 112.dp,
            iconSize = 48.dp,
            shape = RoundedCornerShape(32.dp)
        )

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = "NFCGate",
            fontSize = 38.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Secure NFC connectivity,\nsimplified.",
            fontSize = 17.sp,
            lineHeight = 25.sp,
            color = TextSecondary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(30.dp))

        // ----------------------------------------------------
        // Feature Pills
        // ----------------------------------------------------

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FeaturePill(
                text = "Secure",
                icon = FeatherIcons.Lock
            )

            FeaturePill(
                text = "Fast",
                icon = FeatherIcons.Zap
            )

            FeaturePill(
                text = "Private",
                icon = FeatherIcons.User
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // ----------------------------------------------------
        // CTA
        // ----------------------------------------------------

        PrimaryButton(
            text = "Get Started",
            onClick = onNavigateToLogin
        )

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = "NFC Relay. Reimagined.",
            fontSize = 13.sp,
            color = TextHint
        )

        Spacer(modifier = Modifier.height(28.dp))
    }
}


// ============================================================
// FEATURE PILL
// ============================================================

@Composable
private fun FeaturePill(
    text: String,
    icon: ImageVector
) {
    Surface(
        color = SurfaceColor,
        shape = RoundedCornerShape(50),
        border = BorderStroke(
            width = 1.dp,
            color = SurfaceVariantColor
        )
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = 12.dp,
                vertical = 8.dp
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = PrimaryColor,
                modifier = Modifier.size(14.dp)
            )

            Spacer(modifier = Modifier.width(6.dp))

            Text(
                text = text,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = TextSecondary
            )
        }
    }
}


// ============================================================
// LOGIN SCREEN
// ============================================================

@Composable
fun LoginScreen(
    onLoginClick: (String, String) -> Unit,
    onLoginSuccess: (String) -> Unit,
    isLoading: Boolean = false,
    error: String? = null,
    onBack: () -> Unit = {},
    onForgotPassword: () -> Unit = {},
    onSignUp: () -> Unit = {},
    onGoogleLogin: () -> Unit = {},
    onAppleLogin: () -> Unit = {}
) {

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var passwordVisible by remember {
        mutableStateOf(false)
    }

    var rememberMe by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .systemBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp)
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        // ----------------------------------------------------
        // Back Button
        // ----------------------------------------------------

        MinimalBackButton(
            onClick = onBack
        )

        Spacer(modifier = Modifier.height(40.dp))

        // ----------------------------------------------------
        // Header
        // ----------------------------------------------------

        Text(
            text = "Welcome back.",
            fontSize = 34.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Sign in to your NFCGate account.",
            fontSize = 16.sp,
            color = TextSecondary
        )

        Spacer(modifier = Modifier.height(34.dp))

        // ----------------------------------------------------
        // Email
        // ----------------------------------------------------

        AppTextField(
            value = email,
            onValueChange = {
                email = it
            },
            label = "Email or Username",
            leadingIcon = {
                Icon(
                    imageVector = FeatherIcons.User,
                    contentDescription = null,
                    tint = TextHint
                )
            }
        )

        Spacer(modifier = Modifier.height(14.dp))

        // ----------------------------------------------------
        // Password
        // ----------------------------------------------------

        AppTextField(
            value = password,
            onValueChange = {
                password = it
            },
            label = "Password",
            leadingIcon = {
                Icon(
                    imageVector = FeatherIcons.Lock,
                    contentDescription = null,
                    tint = TextHint
                )
            },
            visualTransformation =
                if (passwordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
            trailingIcon = {

                Icon(
                    imageVector =
                        if (passwordVisible) {
                            FeatherIcons.Eye
                        } else {
                            FeatherIcons.EyeOff
                        },
                    contentDescription = "Toggle password visibility",
                    tint = TextHint,
                    modifier = Modifier
                        .appleClickable {
                            passwordVisible = !passwordVisible
                        }
                )
            }
        )

        Spacer(modifier = Modifier.height(14.dp))

        // ----------------------------------------------------
        // Remember / Forgot
        // ----------------------------------------------------

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            MinimalCheckbox(
                checked = rememberMe,
                onCheckedChange = {
                    rememberMe = it
                }
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Remember me",
                fontSize = 14.sp,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Forgot password?",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = PrimaryColor,
                modifier = Modifier.appleClickable {
                    onForgotPassword()
                }
            )
        }

        Spacer(modifier = Modifier.height(28.dp))

        // ----------------------------------------------------
        // Sign In
        // ----------------------------------------------------

        PrimaryButton(
            text = if (isLoading) {
                "Signing in..."
            } else {
                "Sign In"
            },
            onClick = {
                if (!isLoading) {
                    onLoginClick(
                        email,
                        password
                    )
                }
            }
        )

        // ----------------------------------------------------
        // Error
        // ----------------------------------------------------

        if (error != null) {

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                fontSize = 13.sp,
                lineHeight = 18.sp
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        // ----------------------------------------------------
        // Divider
        // ----------------------------------------------------

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            HorizontalDivider(
                modifier = Modifier.weight(1f),
                color = SurfaceVariantColor
            )

            Text(
                text = "OR",
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = TextHint,
                modifier = Modifier.padding(
                    horizontal = 14.dp
                )
            )

            HorizontalDivider(
                modifier = Modifier.weight(1f),
                color = SurfaceVariantColor
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // ----------------------------------------------------
        // Social Login
        // ----------------------------------------------------

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            SocialButton(
                modifier = Modifier.weight(1f),
                icon = FeatherIcons.Globe,
                text = "Google",
                onClick = onGoogleLogin
            )

            SocialButton(
                modifier = Modifier.weight(1f),
                icon = FeatherIcons.Smartphone,
                text = "Apple",
                onClick = onAppleLogin
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // ----------------------------------------------------
        // Sign Up
        // ----------------------------------------------------

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Don't have an account? ",
                fontSize = 14.sp,
                color = TextSecondary
            )

            Text(
                text = "Sign Up",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = PrimaryColor,
                modifier = Modifier.appleClickable {
                    onSignUp()
                }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}


// ============================================================
// BACK BUTTON
// ============================================================

@Composable
private fun MinimalBackButton(
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .size(42.dp)
            .appleClickable {
                onClick()
            },
        shape = RoundedCornerShape(14.dp),
        color = SurfaceColor,
        border = BorderStroke(
            width = 1.dp,
            color = SurfaceVariantColor
        )
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = TextPrimary,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}


// ============================================================
// CHECKBOX
// ============================================================

@Composable
private fun MinimalCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {

    val background by animateColorAsState(
        targetValue =
            if (checked) {
                PrimaryColor
            } else {
                Color.Transparent
            },
        label = "checkboxBackground"
    )

    val borderColor =
        if (checked) {
            PrimaryColor
        } else {
            OutlineColor
        }

    Box(
        modifier = Modifier
            .size(20.dp)
            .clip(
                RoundedCornerShape(6.dp)
            )
            .background(background)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(6.dp)
            )
            .appleClickable {
                onCheckedChange(!checked)
            },
        contentAlignment = Alignment.Center
    ) {

        if (checked) {

            Icon(
                imageVector = FeatherIcons.Check,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(13.dp)
            )
        }
    }
}


// ============================================================
// SOCIAL BUTTON
// ============================================================

@Composable
private fun SocialButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    text: String,
    onClick: () -> Unit
) {

    Surface(
        modifier = modifier
            .height(50.dp)
            .appleClickable {
                onClick()
            },
        shape = RoundedCornerShape(14.dp),
        color = SurfaceColor,
        border = BorderStroke(
            width = 1.dp,
            color = SurfaceVariantColor
        )
    ) {

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = TextPrimary,
                modifier = Modifier.size(18.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = text,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = TextPrimary
            )
        }
    }
}


// ============================================================
// ROLE SELECTION
// ============================================================

@Composable
fun RoleSelectionScreen(
    onRoleSelected: (String) -> Unit,
    onBack: () -> Unit = {}
) {

    var selectedRole by remember {
        mutableStateOf("ADMIN")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .systemBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp)
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        // ----------------------------------------------------
        // Back
        // ----------------------------------------------------

        MinimalBackButton(
            onClick = onBack
        )

        Spacer(modifier = Modifier.height(40.dp))

        // ----------------------------------------------------
        // Header
        // ----------------------------------------------------

        Text(
            text = "How will you use\nNFCGate?",
            fontSize = 34.sp,
            lineHeight = 40.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Select your role to personalize your experience.",
            fontSize = 16.sp,
            lineHeight = 23.sp,
            color = TextSecondary
        )

        Spacer(modifier = Modifier.height(32.dp))

        // ----------------------------------------------------
        // Administrator
        // ----------------------------------------------------

        RoleCard(
            title = "Administrator",
            description = "Manage users, devices, sessions and system settings.",
            icon = FeatherIcons.Award,
            isSelected = selectedRole == "ADMIN",
            onClick = {
                selectedRole = "ADMIN"
                onRoleSelected("ADMIN")
            }
        )

        Spacer(modifier = Modifier.height(14.dp))

        // ----------------------------------------------------
        // User
        // ----------------------------------------------------

        RoleCard(
            title = "User",
            description = "Connect to NFC and start your relay session.",
            icon = FeatherIcons.User,
            isSelected = selectedRole == "USER",
            onClick = {
                selectedRole = "USER"
                onRoleSelected("USER")
            }
        )

        Spacer(modifier = Modifier.weight(1f))

        // ----------------------------------------------------
        // Info
        // ----------------------------------------------------

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = FeatherIcons.Info,
                contentDescription = null,
                tint = TextHint,
                modifier = Modifier.size(15.dp)
            )

            Spacer(modifier = Modifier.width(7.dp))

            Text(
                text = "You can change this later in Settings.",
                fontSize = 13.sp,
                color = TextHint
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}


// ============================================================
// ROLE CARD
// ============================================================

@Composable
fun RoleCard(
    title: String,
    description: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {

    val borderColor by animateColorAsState(
        targetValue =
            if (isSelected) {
                PrimaryColor
            } else {
                SurfaceVariantColor
            },
        label = "roleBorder"
    )

    val iconBackground by animateColorAsState(
        targetValue =
            if (isSelected) {
                PrimaryTint
            } else {
                SurfaceVariantColor
            },
        label = "roleIcon"
    )

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .appleClickable {
                onClick()
            },
        shape = RoundedCornerShape(20.dp),
        color = SurfaceColor,
        border = BorderStroke(
            width = if (isSelected) 1.5.dp else 1.dp,
            color = borderColor
        )
    ) {

        Row(
            modifier = Modifier.padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // ------------------------------------------------
            // Icon
            // ------------------------------------------------

            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(
                        RoundedCornerShape(15.dp)
                    )
                    .background(iconBackground),
                contentAlignment = Alignment.Center
            ) {

                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = PrimaryColor,
                    modifier = Modifier.size(23.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // ------------------------------------------------
            // Text
            // ------------------------------------------------

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = title,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = description,
                    fontSize = 13.sp,
                    lineHeight = 19.sp,
                    color = TextSecondary
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // ------------------------------------------------
            // Selection
            // ------------------------------------------------

            SelectionIndicator(
                selected = isSelected
            )
        }
    }
}


// ============================================================
// SELECTION INDICATOR
// ============================================================

@Composable
private fun SelectionIndicator(
    selected: Boolean
) {

    val color by animateColorAsState(
        targetValue =
            if (selected) {
                PrimaryColor
            } else {
                TextHint
            },
        label = "selectionColor"
    )

    Box(
        modifier = Modifier
            .size(22.dp)
            .border(
                width = 1.5.dp,
                color = color,
                shape = CircleShape
            )
            .padding(4.dp)
    ) {

        if (selected) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = PrimaryColor,
                        shape = CircleShape
                    )
            )
        }
    }
}