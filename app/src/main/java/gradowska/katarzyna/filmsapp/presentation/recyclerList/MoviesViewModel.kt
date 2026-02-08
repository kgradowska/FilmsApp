package gradowska.katarzyna.filmsapp.presentation.recyclerList

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gradowska.katarzyna.filmsapp.domain.entity.MovieDataModel
import gradowska.katarzyna.filmsapp.domain.usecase.GetMoviesUseCase
import gradowska.katarzyna.filmsapp.domain.usecase.GetSearchedMovieDetailsUseCase
import gradowska.katarzyna.filmsapp.domain.usecase.SetFavouriteMovieUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val setFavouriteMovieUseCase: SetFavouriteMovieUseCase,
    private val getMoviesUseCase: GetMoviesUseCase,
    private val getSearchedMovieUseCase: GetSearchedMovieDetailsUseCase
) : ViewModel() {

    private var isLoading = false
    private var canLoadMore = true
    private var currentPage = 1
    private var currentQuery = ""

    private val _moviesList: MutableStateFlow<List<MovieDataModel>> = MutableStateFlow(emptyList())
    val moviesList: StateFlow<List<MovieDataModel>> = _moviesList

    private val _showToast = MutableSharedFlow<Unit>()
    val showToast = _showToast.asSharedFlow()

    init {
        getMoviesList()
        viewModelScope.launch {
            delay(2000)
            _showToast.emit(Unit) // TODO add a toast in compose
        }
    }

    fun recyclerEndReached() {
        if (isLoading || !canLoadMore) return
        if (currentQuery.isEmpty()) {
            getMoviesList()
        } else {
            getSearchedMovies()
        }
    }

    private fun getMoviesList() {
        if (!isLoading && canLoadMore) {
            viewModelScope.launch {
                try {
                    isLoading = true
                    val movieList = getMoviesUseCase.getMoviesList(true, currentPage)

                    if (currentPage == 1) {
                        _moviesList.value = movieList
                    } else {
                        _moviesList.value = _moviesList.value + movieList
                    }

                    isLoading = false
                    canLoadMore = movieList.isNotEmpty()
                    currentPage++
                } catch (exception: Exception) {
                    isLoading = false
                    Log.e("getMovies", "Exception: ${exception.message}")
                }
            }
        }
    }

    private fun getSearchedMovies() {
        if (!isLoading && canLoadMore) {
            viewModelScope.launch {
                try {
                    isLoading = true
                    val movieList =
                        getSearchedMovieUseCase.getSearchedMovieList(currentQuery, currentPage)

                    if (currentPage == 1) {
                        _moviesList.value = movieList
                    } else {
                        _moviesList.value = _moviesList.value + movieList
                    }

                    isLoading = false
                    canLoadMore = movieList.isNotEmpty()
                    currentPage++
                } catch (exception: Exception) {
                    isLoading = false
                    Log.e("getSearchedMovies", "Exception: ${exception.message}")
                }
            }
        }
    }

    fun onFavouriteResultReceived(
        // TODO add database
        isFavourite: Boolean,
        movieID: String?,
    ) {
        _moviesList.value = _moviesList.value.map { movie ->
            if (movie.movieID == movieID) movie.copy(movieLiked = isFavourite) else movie
        }
    }

    fun searchClicked(query: String) {
        if (!isLoading) {
            if (currentQuery != query) {
                currentPage = 1
                currentQuery = query
                _moviesList.value = emptyList()
                canLoadMore = true
            }

            if (currentPage == 1) {
                if (currentQuery.isEmpty()) {
                    getMoviesList()
                } else {
                    getSearchedMovies()
                }
            }
        }
    }

    fun favouriteIconClicked(movie: MovieDataModel) {
        viewModelScope.launch {
            val newLikedStatus = !movie.movieLiked
            setFavouriteMovieUseCase.setMovieIsFavourite(movie.movieID, newLikedStatus)

            _moviesList.value = _moviesList.value.map { m ->
                if (m.movieID == movie.movieID) {
                    m.copy(movieLiked = newLikedStatus)
                } else {
                    m
                }
            }
        }
    }
}