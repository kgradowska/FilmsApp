package gradowska.katarzyna.filmsapp.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import gradowska.katarzyna.filmsapp.presentation.navigation.MainScreen
import gradowska.katarzyna.filmsapp.presentation.theme.FilmsAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        setContent {
            FilmsAppTheme {
                MainScreen()
            }
        }
    }
}