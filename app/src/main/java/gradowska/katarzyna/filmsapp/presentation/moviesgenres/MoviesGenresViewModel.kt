package gradowska.katarzyna.filmsapp.presentation.moviesgenres

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gradowska.katarzyna.filmsapp.domain.entity.GenreDataModel
import gradowska.katarzyna.filmsapp.domain.entity.MovieDataModel
import gradowska.katarzyna.filmsapp.domain.usecase.GetGenresUseCase
import gradowska.katarzyna.filmsapp.domain.usecase.GetMoviesGenresUseCase
import gradowska.katarzyna.filmsapp.domain.usecase.SetFavouriteMovieUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MoviesGenresViewModel(
    private val getMoviesGenresUseCase: GetMoviesGenresUseCase,
    private val setFavouriteMovieUseCase: SetFavouriteMovieUseCase,
    private val getGenresUseCase: GetGenresUseCase
) : ViewModel() {

    private val _moviesList: MutableStateFlow<List<MovieDataModel>> = MutableStateFlow(listOf())
    val moviesList: StateFlow<List<MovieDataModel>> = _moviesList

    private val _genresList: MutableStateFlow<List<GenreDataModel>> = MutableStateFlow(listOf())
    val genresList: StateFlow<List<GenreDataModel>> = _genresList

    var genreId: Int? = null

    var spinnerValues: List<Float> = listOf(0F, 10F)

    init {
        viewModelScope.launch {
            getGenres()
            getMoviesList(null, null, null)
        }
    }

    private fun getMoviesList(genreId: Int?, voteAverageGte: Float?, voteAverageLte: Float?) {
        viewModelScope.launch {
            val movieList = getMoviesGenresUseCase.getMovieList(
                query = null,
                currentPage = 1,
                withGenres = genreId?.toString(),
                voteAverageGte = voteAverageGte,
                voteAverageLte = voteAverageLte
            )
            /*val allMovies = if (true) {
                ArrayList()
            } else {
                ArrayList(_moviesList.value)
            }
            allMovies.addAll(movieList)
            _moviesList.value = allMovies*/
            _moviesList.value = movieList

        }
    }

    fun favouriteIconClicked(movie: MovieDataModel) {
        setFavouriteMovieUseCase.setMovieIsFavourite(movie.movieID, !movie.movieLiked)
        val newList = ArrayList<MovieDataModel>()
        for (m in _moviesList.value) {
            if (m.movieID != movie.movieID) {
                newList.add(m)
            } else {
                newList.add(m.copy(movieLiked = !movie.movieLiked))
            }
        }

        _moviesList.value = newList
    }

    fun searchClicked() {
        if (spinnerValues.size == 2) {
            getMoviesList(genreId, spinnerValues[0], spinnerValues[1])
        }
    }

    private suspend fun getGenres() {
        val genres = getGenresUseCase.getGenres()
        _genresList.emit(genres)
    }
}