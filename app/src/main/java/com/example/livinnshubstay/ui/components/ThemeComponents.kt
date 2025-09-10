package com.example.livinnshubstay.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.livinnshubstay.ui.theme.NeonGreen
import com.example.livinnshubstay.ui.theme.SoftViolet
import com.example.livinnshubstay.ui.theme.TagPurple
import com.example.livinnshubstay.ui.theme.TagRed
import com.example.livinnshubstay.ui.theme.TextWhite
import com.example.livinnshubstay.ui.theme.VibrantPurple

// Header with highlighted text
@Composable
fun HeaderWithHighlight(
    text: String,
    highlightText: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = buildAnnotatedString {
            append(text)
            append(" ")
            withStyle(
                style = SpanStyle(
                    color = NeonGreen,
                    fontWeight = FontWeight.Bold
                )
            ) {
                append(highlightText)
            }
        },
        style = MaterialTheme.typography.displayLarge,
        modifier = modifier
    )
}

// Call-to-action button with arrow
@Composable
fun ActionButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = VibrantPurple
        ),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp),
        modifier = modifier
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall,
            color = TextWhite
        )
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = null,
            tint = TextWhite
        )
    }
}

// Circular icon button with gradient background
@Composable
fun CircularIconButton(
    icon: ImageVector,
    contentDescription: String?,
    onClick: () -> Unit,
    showNotification: Boolean = false,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Surface(
            shape = CircleShape,
            color = Color.Transparent,
            modifier = Modifier.size(56.dp),
            onClick = onClick
        ) {
            Box(
                modifier = Modifier
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(VibrantPurple, SoftViolet)
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = contentDescription,
                    tint = TextWhite,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        
        if (showNotification) {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .clip(CircleShape)
                    .background(NeonGreen)
                    .align(Alignment.TopEnd)
            )
        }
    }
}

// Card with dark background
@Composable
fun DarkCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        modifier = modifier
    ) {
        Box(
            modifier = Modifier.padding(16.dp)
        ) {
            content()
        }
    }
}

// Tag component for offers, sales, etc.
@Composable
fun Tag(
    text: String,
    isOffer: Boolean = true,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = if (isOffer) TagPurple else TagRed,
        modifier = modifier
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            color = TextWhite,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

// Bottom navigation FAB
@Composable
fun PrimaryActionFAB(
    icon: ImageVector,
    contentDescription: String?,
    onClick: () -> Unit,
    isSelected: Boolean = true,
    modifier: Modifier = Modifier
) {
    FloatingActionButton(
        onClick = onClick,
        containerColor = if (isSelected) VibrantPurple else VibrantPurple.copy(alpha = 0.7f),
        contentColor = TextWhite,
        shape = CircleShape,
        modifier = modifier
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            modifier = Modifier.size(24.dp)
        )
    }
}