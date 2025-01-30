package gradowska.katarzyna.filmsapp.presentation.moviesgenres

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gradowska.katarzyna.filmsapp.domain.entity.GenreDataModel
import gradowska.katarzyna.filmsapp.domain.entity.MovieDataModel
import gradowska.katarzyna.filmsapp.domain.usecase.GetGenresUseCase
import gradowska.katarzyna.filmsapp.domain.usecase.GetMoviesGenresUseCase
import gradowska.katarzyna.filmsapp.domain.usecase.SetFavouriteMovieUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
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

    private val _rangeValues = MutableStateFlow(listOf(INITIAL_MIN_RANGE, INITIAL_MAX_RANGE))
    val rangeValues: StateFlow<List<Float>> = _rangeValues

    private val _hideAppBarLayout = MutableSharedFlow<Unit>()
    val hideAppBarLayout = _hideAppBarLayout.asSharedFlow()

    private var fetchJob: Job? = null

    var genreId: Int? = null
    private var isLoading = false
    private var canLoadMore = true
    private var currentPage = 1

    companion object {
        private const val INITIAL_MIN_RANGE = 6.5f
        private const val INITIAL_MAX_RANGE = 8.2f
    }

    private var currentMinRange = INITIAL_MIN_RANGE
    private var currentMaxRange = INITIAL_MAX_RANGE

    init {
        viewModelScope.launch {
            val genres = getGenres()
            genreId = genres.firstOrNull()?.id
            getMoviesList()
        }
    }

    fun listEndReached() {
        getMoviesList()
    }

    fun onSliderRangeChanged(selectedValues: List<Float>) {
        _rangeValues.value = selectedValues
    }

    private fun getMoviesList() {
        if (!isLoading && canLoadMore) {
            fetchJob?.cancel()
            fetchJob = viewModelScope.launch {
                try {
                    isLoading = true
                    val movieList = getMoviesGenresUseCase.getMovieList(
                        query = null,
                        currentPage = currentPage,
                        withGenres = genreId?.toString(),
                        voteAverageGte = currentMinRange,
                        voteAverageLte = currentMaxRange,
                    )
                    val allMovies = if (currentPage == 1) {
                        ArrayList()
                    } else {
                        ArrayList(_moviesList.value)
                    }
                    allMovies.addAll(movieList)
                    _moviesList.value = allMovies

                    isLoading = false
                    canLoadMore = movieList.isNotEmpty()
                    currentPage++
                } catch (exception: Exception) {
                    isLoading = false
                    Log.e("getMoviesGenres", "Exception: ${exception.message}")
                }
            }
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

    fun searchButtonClicked() {
        setStartValues()
        getMoviesList()
        viewModelScope.launch {
            _hideAppBarLayout.emit(Unit)
        }
    }

    private fun setStartValues() {
        currentPage = 1
        _moviesList.value = emptyList()
        canLoadMore = true
        isLoading = false
        currentMinRange = _rangeValues.value[0]
        currentMaxRange = _rangeValues.value[1]
    }

    private suspend fun getGenres(): List<GenreDataModel> {
        val genres = getGenresUseCase.getGenres()
        _genresList.emit(genres)
        return genres
    }
}