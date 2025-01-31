package gradowska.katarzyna.filmsapp.presentation.recyclerList

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gradowska.katarzyna.filmsapp.domain.usecase.GetMoviesUseCase
import gradowska.katarzyna.filmsapp.domain.usecase.SetFavouriteMovieUseCase
import gradowska.katarzyna.filmsapp.domain.entity.MovieDataModel
import gradowska.katarzyna.filmsapp.domain.usecase.GetSearchedMovieDetailsUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val setFavouriteMovieUseCase: SetFavouriteMovieUseCase,
    private val getMoviesUseCase: GetMoviesUseCase,
    private val getSearchedMovieUseCase: GetSearchedMovieDetailsUseCase
) : ViewModel() {

    private var isLoading = false
    private var canLoadMore = true
    private var currentPage = 1

    private val _moviesList: MutableStateFlow<List<MovieDataModel>> = MutableStateFlow(listOf())
    val moviesList: StateFlow<List<MovieDataModel>> = _moviesList

    private val _showToast = MutableSharedFlow<Unit>()
    val showToast = _showToast.asSharedFlow()

    private var currentQuery = ""

    init {
        getMoviesList()
        viewModelScope.launch {
            delay(2000)
            _showToast.emit(Unit)
        }
    }

    fun recyclerEndReached() {
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
                    Log.e("getSearchedMovies", "Exception: ${exception.message}")
                }
            }
        }
    }

    fun searchClicked(query: String) {
        if (!isLoading) {
            if (currentQuery != query) {
                currentPage = 1
                canLoadMore = true
                isLoading = false
            }

            currentQuery = query

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
}