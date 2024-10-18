package com.plfdev.crypto_tracker.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.plfdev.crypto_tracker.R


val SpaceMono = FontFamily(
    Font(
        resId = R.font.spacemono_regular,
        weight = FontWeight.Normal
    ),
    Font(
        resId = R.font.spacemono_italic,
        weight = FontWeight.Normal,
        style = FontStyle.Italic
    ),
    Font(
        resId = R.font.spacemono_bold,
        weight = FontWeight.Bold
    ),
    Font(
        resId = R.font.spacemono_bolditalic,
        weight = FontWeight.Bold,
        style = FontStyle.Italic
    ),
)

val CrytoListTypography = Typography(
    bodySmall = TextStyle(
        fontFamily = SpaceMono,
        fontWeight = FontWeight.Light,
        fontSize = 12.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = SpaceMono,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = SpaceMono,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    labelMedium = TextStyle(
        fontFamily = SpaceMono,
        fontWeight = FontWeight.Normal,
    ),
    headlineMedium = TextStyle(
        fontFamily = SpaceMono,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    )
)
