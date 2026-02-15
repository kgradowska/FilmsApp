package gradowska.katarzyna.filmsapp.presentation.singleMovie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gradowska.katarzyna.filmsapp.domain.entity.MovieDetailsDataModel
import gradowska.katarzyna.filmsapp.domain.usecase.GetMovieDetailsUseCase
import gradowska.katarzyna.filmsapp.domain.usecase.SetFavouriteMovieUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SingleMovieViewModel(
    movieId: String,
    getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val setFavouriteMovieUseCase: SetFavouriteMovieUseCase
) : ViewModel() {

    val movieDetails: StateFlow<MovieDetailsDataModel?> = getMovieDetailsUseCase.getMovie(movieId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null,
        )

    fun favouriteIconClicked(movie: MovieDetailsDataModel) {
        viewModelScope.launch {
            setFavouriteMovieUseCase.setMovieIsFavourite(movie.movieID, !movie.movieLiked)
        }
    }
}
