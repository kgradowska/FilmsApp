package gradowska.katarzyna.filmsapp.presentation.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import gradowska.katarzyna.filmsapp.R
import gradowska.katarzyna.filmsapp.presentation.filters.FilterScreen
import gradowska.katarzyna.filmsapp.presentation.searchMovies.MoviesScreen
import gradowska.katarzyna.filmsapp.presentation.singleMovie.SingleMovieScreen

@Composable
fun MainNavGraph(navController: NavHostController) {

    val context = LocalContext.current
    val errorMessage = stringResource(R.string.error_invalid_movie_id)

    NavHost(
        navController = navController,
        startDestination = MoviesHome
    ) {
        composable<MoviesHome> {
            MoviesScreen(onMovieClick = { id ->
                navigateToDetails(navController, id, onError = {
                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                })
            })
        }

        composable<MoviesFilter> {
            FilterScreen(onMovieClick = { id ->
                navigateToDetails(navController, id, onError = {
                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                })
            })
        }

        composable<MovieDetails> { backStackEntry ->
            val detailsArgs = backStackEntry.toRoute<MovieDetails>()
            SingleMovieScreen(detailsArgs.movieId)
        }
    }
}

private fun navigateToDetails(
    navController: NavHostController,
    idString: String,
    onError: () -> Unit
) {
    val id = idString.toIntOrNull()
    if (id != null) {
        navController.navigate(MovieDetails(id))
    } else {
        onError()
    }
}
