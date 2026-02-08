package gradowska.katarzyna.filmsapp.presentation.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import gradowska.katarzyna.filmsapp.presentation.recyclerList.compose.MoviesScreen

@Composable
fun MainNavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = MoviesHome
    ) {
        composable<MoviesHome> {
            MoviesScreen(onMovieClick = { id ->
                navController.navigate(MovieDetails(id.toInt())) // TODO double check it later
            })
        }

        composable<MoviesFilter> {
            Text("FILTER SCREEN")
        }

        composable<MovieDetails> { backStackEntry ->
            val detailsArgs = backStackEntry.toRoute<MovieDetails>()
            Text(text = "id ${detailsArgs.movieId}")
        }
    }
}