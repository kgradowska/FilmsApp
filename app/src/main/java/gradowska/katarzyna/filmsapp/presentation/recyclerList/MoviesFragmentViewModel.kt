package gradowska.katarzyna.filmsapp.presentation.recyclerList

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gradowska.katarzyna.filmsapp.domain.usecase.GetMoviesUseCase
import gradowska.katarzyna.filmsapp.domain.usecase.SetFavouriteMovieUseCase
import gradowska.katarzyna.filmsapp.domain.entity.MovieDataModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MoviesFragmentViewModel(
    private val setFavouriteMovieUseCase: SetFavouriteMovieUseCase,
    private val getMoviesUseCase: GetMoviesUseCase,
) : ViewModel() {

    private val _moviesList: MutableStateFlow<List<MovieDataModel>> = MutableStateFlow(listOf())
    val moviesList: StateFlow<List<MovieDataModel>> = _moviesList

    init {
        getMoviesList()
    }

    private fun getMoviesList() {
        viewModelScope.launch {
            try {
                val movieList = getMoviesUseCase.getMoviesList(true)
                _moviesList.emit(movieList)
            } catch (exception: Exception) {
                Log.e("getMovies", "Exception: ${exception.message}")
            }
        }
    }

    fun favouriteIconClicked(movie: MovieDataModel) {
        setFavouriteMovieUseCase.setMovieIsFavourite(movie.movieID, !movie.movieLiked)
        getMoviesList()
    }
}