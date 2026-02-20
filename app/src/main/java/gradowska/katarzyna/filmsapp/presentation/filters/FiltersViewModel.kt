package gradowska.katarzyna.filmsapp.presentation.filters

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gradowska.katarzyna.filmsapp.domain.entity.GenreDataModel
import gradowska.katarzyna.filmsapp.domain.entity.MovieDataModel
import gradowska.katarzyna.filmsapp.domain.usecase.GetFavouriteMoviesUseCase
import gradowska.katarzyna.filmsapp.domain.usecase.GetGenresUseCase
import gradowska.katarzyna.filmsapp.domain.usecase.GetMoviesGenresUseCase
import gradowska.katarzyna.filmsapp.domain.usecase.SetFavouriteMovieUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

class FiltersViewModel(
    private val getMoviesGenresUseCase: GetMoviesGenresUseCase,
    private val setFavouriteMovieUseCase: SetFavouriteMovieUseCase,
    private val getGenresUseCase: GetGenresUseCase,
    getFavouriteMoviesUseCase: GetFavouriteMoviesUseCase,
) : ViewModel() {

    private val _moviesList = MutableStateFlow<PersistentList<MovieDataModel>>(persistentListOf())
    val moviesList: StateFlow<PersistentList<MovieDataModel>> =
        _moviesList.combine(getFavouriteMoviesUseCase()) { movies, favourites ->
            movies.map { m ->
                m.copy(movieLiked = favourites.contains(m.movieID))
            }.toPersistentList()
        }.stateIn(
            scope = viewModelScope,
            initialValue = _moviesList.value,
            started = SharingStarted.WhileSubscribed(5000),
        )

    private val _genresList = MutableStateFlow<PersistentList<GenreDataModel>>(persistentListOf())
    val genresList: StateFlow<PersistentList<GenreDataModel>> = _genresList

    private val _rangeValues = MutableStateFlow(listOf(INITIAL_MIN_RANGE, INITIAL_MAX_RANGE))
    val rangeValues: StateFlow<List<Float>> = _rangeValues

    private var fetchJob: Job? = null

    private val _selectedGenreId = MutableStateFlow<Int?>(null)
    val selectedGenreId: StateFlow<Int?> = _selectedGenreId

    private var isLoading = false
    private var canLoadMore = true
    private var currentPage = 1

    private var currentMinRange = INITIAL_MIN_RANGE
    private var currentMaxRange = INITIAL_MAX_RANGE

    init {
        viewModelScope.launch {
            val genres = getGenres()
            _selectedGenreId.value = genres.firstOrNull()?.id
            getMoviesList()
        }
    }

    fun listEndReached() {
        getMoviesList()
    }

    fun onSliderRangeChanged(selectedValues: List<Float>) {
        _rangeValues.value = selectedValues
    }

    fun onGenreSelected(id: Int?) {
        _selectedGenreId.value = id
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
                        withGenres = _selectedGenreId.value?.toString(),
                        voteAverageGte = currentMinRange,
                        voteAverageLte = currentMaxRange,
                    )

                    val newList = if (currentPage == 1) {
                        movieList.toPersistentList()
                    } else {
                        (_moviesList.value + movieList).distinctBy { it.movieID }.toPersistentList()
                    }

                    _moviesList.value = newList

                    isLoading = false
                    canLoadMore = movieList.isNotEmpty()
                    currentPage++
                } catch (exception: Exception) {
                    isLoading = false
                    _moviesList.value = persistentListOf()
                    Log.e("getMoviesGenres", "Exception: ${exception.message}")
                }
            }
        }
    }

    fun favouriteIconClicked(movie: MovieDataModel) {
        viewModelScope.launch {
            setFavouriteMovieUseCase.setMovieIsFavourite(movie.movieID, !movie.movieLiked)
        }
    }

    fun searchButtonClicked() {
        setStartValues()
        getMoviesList()
    }

    private fun setStartValues() {
        currentPage = 1
        canLoadMore = true
        isLoading = false
        currentMinRange = _rangeValues.value[0]
        currentMaxRange = _rangeValues.value[1]
    }

    private suspend fun getGenres(): List<GenreDataModel> {
        val genres = getGenresUseCase.getGenres()
        _genresList.emit(genres.toPersistentList())
        return genres
    }

    companion object {
        private const val INITIAL_MIN_RANGE = 5f
        private const val INITIAL_MAX_RANGE = 7f
    }
}
