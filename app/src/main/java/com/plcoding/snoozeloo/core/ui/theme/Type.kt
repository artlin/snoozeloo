package com.plcoding.snoozeloo.core.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.sp
import com.plcoding.snoozeloo.R

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)

val MontserratFamily = FontFamily(
    Font(R.font.montserrat_thin, FontWeight.Thin),
    Font(R.font.montserrat_light, FontWeight.Light),
    Font(R.font.montserrat_regular, FontWeight.Normal),
    Font(R.font.montserrat_medium, FontWeight.Medium),
    Font(R.font.montserrat_semibold, FontWeight.SemiBold),
    Font(R.font.montserrat_bold, FontWeight.Bold),
    Font(R.font.montserrat_extrabold, FontWeight.ExtraBold),
    Font(R.font.montserrat_black, FontWeight.Black)
)

val LocalTextStyleTokens = staticCompositionLocalOf<FontTokens> {
    error("No text style tokens provided")
}

class FontTokens : TemplateTypeTokens(
    // 10:00 on snooze screen
    h1 = TextStyle(
        fontFamily = MontserratFamily,
        fontWeight = FontWeight.Medium,
        fontSize = TextUnit(82f, TextUnitType.Sp)
    ),
    // 16:45
    h2 = TextStyle(
        fontFamily = MontserratFamily,
        fontWeight = FontWeight.Medium,
        fontSize = TextUnit(52f, TextUnitType.Sp)
    ),
    // 06:00
    h3 = TextStyle(
        fontFamily = MontserratFamily,
        fontWeight = FontWeight.Medium,
        fontSize = TextUnit(42f, TextUnitType.Sp)
    ),
    // WORK on snooze screen
    title1Strong = TextStyle(
        fontFamily = MontserratFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = TextUnit(24f, TextUnitType.Sp)
    ),
    // YOUR ALARMS HEADER
    title1 = TextStyle(
        fontFamily = MontserratFamily,
        fontWeight = FontWeight.Medium,
        fontSize = TextUnit(24f, TextUnitType.Sp)
    ),
    // Dinner,Alarm Name
    title2Strong = TextStyle(
        fontFamily = MontserratFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = TextUnit(16f, TextUnitType.Sp)
    ),
    //It's empty! Add the first alarm so you
    title2 = TextStyle(
        fontFamily = MontserratFamily,
        fontWeight = FontWeight.Medium,
        fontSize = TextUnit(16f, TextUnitType.Sp)
    ),
    // alarm sound name
    bodyStrong = TextStyle(
        fontFamily = MontserratFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = TextUnit(14f, TextUnitType.Sp)
    ),
    // Alarm in 6h 30min
    body = TextStyle(
        fontFamily = MontserratFamily,
        fontWeight = FontWeight.Medium,
        fontSize = TextUnit(14f, TextUnitType.Sp)
    ),
    // selecting alarm day
    label = TextStyle(
        fontFamily = MontserratFamily,
        fontWeight = FontWeight.Medium,
        fontSize = TextUnit(12f, TextUnitType.Sp)
    ),
)

@Immutable
open class TemplateTypeTokens(
    open val h1: TextStyle,
    open val h2: TextStyle,
    open val h3: TextStyle,
    open val title1Strong: TextStyle,
    open val title1: TextStyle,
    open val title2Strong: TextStyle,
    open val title2: TextStyle,
    open val bodyStrong: TextStyle,
    open val body: TextStyle,
    open val label: TextStyle,
)
