package gradowska.katarzyna.filmsapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import gradowska.katarzyna.filmsapp.presentation.recyclerList.MoviesViewModel
import gradowska.katarzyna.filmsapp.presentation.recyclerList.compose.FilterScreen
import gradowska.katarzyna.filmsapp.presentation.recyclerList.compose.MoviesScreen
import gradowska.katarzyna.filmsapp.presentation.recyclerList.compose.SingleMovieScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainNavGraph(navController: NavHostController) {

    val moviesViewModel: MoviesViewModel = koinViewModel()

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
            SingleMovieScreen(detailsArgs.movieId, onFavouriteChanged = { id, isLiked ->
                moviesViewModel.onFavouriteResultReceived(isLiked, id)
            })
        }
    }
}