package gradowska.katarzyna.filmsapp.presentation.singleMovie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gradowska.katarzyna.filmsapp.domain.entity.MovieDetailsDataModel
import gradowska.katarzyna.filmsapp.domain.usecase.GetMovieDetailsUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class SingleMovieViewModel(
    private val movieId: String,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase
) : ViewModel() {

    private val _movieDetails: MutableSharedFlow<MovieDetailsDataModel> = MutableSharedFlow()
    val movieDetails: SharedFlow<MovieDetailsDataModel> = _movieDetails

    init {
        viewModelScope.launch {
            getMovie()
        }
    }

    private suspend fun getMovie() {
        val newMovie = getMovieDetailsUseCase.getMovie(movieId)
        _movieDetails.emit(newMovie)
    }
}
