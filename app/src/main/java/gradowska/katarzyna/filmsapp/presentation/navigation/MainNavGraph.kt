package gradowska.katarzyna.filmsapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import gradowska.katarzyna.filmsapp.presentation.recyclerList.compose.FilterScreen
import gradowska.katarzyna.filmsapp.presentation.recyclerList.compose.MoviesScreen
import gradowska.katarzyna.filmsapp.presentation.recyclerList.compose.SingleMovieScreen

@Composable
fun MainNavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = MoviesHome
    ) {
        composable<MoviesHome> {
            MoviesScreen(onMovieClick = { id ->
                navController.navigate(MovieDetails(id.toInt()))
            })
        }

        composable<MoviesFilter> {
            FilterScreen(onMovieClick = { id ->
                navController.navigate(MovieDetails(id.toInt()))
            })
        }

        composable<MovieDetails> { backStackEntry ->
            val detailsArgs = backStackEntry.toRoute<MovieDetails>()
            SingleMovieScreen(detailsArgs.movieId)
        }
    }
}
