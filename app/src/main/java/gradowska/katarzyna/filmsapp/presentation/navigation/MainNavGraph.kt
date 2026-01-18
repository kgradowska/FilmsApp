package gradowska.katarzyna.filmsapp.presentation.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun MainNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = MoviesHome
    ) {
        composable<MoviesHome> {
            Column {
                Text("HOME TEST")
            }
        }

        composable<MoviesFilter> {
            Text("FILTER SCREEN")
        }
    }
}