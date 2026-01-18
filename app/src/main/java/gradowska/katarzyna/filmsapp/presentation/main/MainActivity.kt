package gradowska.katarzyna.filmsapp.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

import gradowska.katarzyna.filmsapp.presentation.navigation.MainScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        setContent {
            //FilmsAppTheme { TODO do it later - create Theme and Colors files
            MainScreen()
            //}
        }
    }
}