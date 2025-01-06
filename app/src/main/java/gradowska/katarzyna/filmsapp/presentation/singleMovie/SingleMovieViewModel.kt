package gradowska.katarzyna.filmsapp.presentation.singleMovie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gradowska.katarzyna.filmsapp.domain.entity.MovieDetailsDataModel
import gradowska.katarzyna.filmsapp.domain.usecase.GetMovieDetailsUseCase
import gradowska.katarzyna.filmsapp.domain.usecase.SetFavouriteMovieUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SingleMovieViewModel(
    private val movieId: String,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val setFavouriteMovieUseCase: SetFavouriteMovieUseCase
) : ViewModel() {

    private val _movieDetails: MutableStateFlow<MovieDetailsDataModel?> = MutableStateFlow(null)
    val movieDetails: StateFlow<MovieDetailsDataModel?> = _movieDetails

    init {
        viewModelScope.launch {
            getMovie()
        }
    }

    private suspend fun getMovie() {
        val newMovie = getMovieDetailsUseCase.getMovie(movieId)
        _movieDetails.emit(newMovie)
    }

    fun favouriteIconClicked(movie: MovieDetailsDataModel) {
        setFavouriteMovieUseCase.setMovieIsFavourite(movie.movieID, !movie.movieLiked)
        viewModelScope.launch {
            getMovie()
        }
    }
}
