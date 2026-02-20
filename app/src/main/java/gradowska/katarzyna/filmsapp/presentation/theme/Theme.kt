package gradowska.katarzyna.filmsapp.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class FilmsAppColors(
    val primary: Color,
    val secondary: Color,
    val background: Color,
    val onPrimary: Color
)

val LocalFilmsColors = staticCompositionLocalOf {
    FilmsAppColors(
        primary = WineBerry2,
        secondary = Gold,
        background = Tolopea,
        onPrimary = White
    )
}

object FilmsTheme {
    val colors: FilmsAppColors
        @Composable
        get() = LocalFilmsColors.current
}

@Composable
fun FilmsAppTheme(
    content: @Composable () -> Unit
) {
    val colors = FilmsAppColors(
        primary = WineBerry2,
        secondary = Gold,
        background = Tolopea,
        onPrimary = White
    )

    CompositionLocalProvider(LocalFilmsColors provides colors) {
        MaterialTheme(
            colorScheme = darkColorScheme(
                primary = WineBerry2,
                secondary = Gold,
                surface = Gold,
                background = Tolopea,
                onSurface = WineBerry2,
                onSurfaceVariant = Tolopea,
                onSecondaryContainer = Tolopea
            ), content = content
        )
    }
}