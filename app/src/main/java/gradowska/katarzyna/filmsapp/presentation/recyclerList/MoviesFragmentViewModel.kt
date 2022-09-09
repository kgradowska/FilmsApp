package gradowska.katarzyna.filmsapp.presentation.recyclerList

import androidx.lifecycle.ViewModel
import gradowska.katarzyna.filmsapp.R
import gradowska.katarzyna.filmsapp.data.MovieDataSource
import gradowska.katarzyna.filmsapp.domain.GetMoviesUseCase
import gradowska.katarzyna.filmsapp.presentation.movie.MovieDataModel

class MoviesFragmentViewModel : ViewModel() {
    private val movieList = GetMoviesUseCase(MovieDataSource()).getMoviesList()

    fun getMoviesList(): List<MovieDataModel> {
        return movieList
    }

    fun favouriteIconClicked(movie: MovieDataModel) {
        for (m in movieList) {
            if (m.movieID == movie.movieID) {
                m.movieLiked = !m.movieLiked
            }
        }
    }
}