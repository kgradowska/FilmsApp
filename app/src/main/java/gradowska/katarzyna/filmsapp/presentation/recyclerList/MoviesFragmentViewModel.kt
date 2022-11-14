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

    private var isLoading = false
    private var canLoadMore = true
    private var currentPage = 1

    private val _moviesList: MutableStateFlow<List<MovieDataModel>> = MutableStateFlow(listOf())
    val moviesList: StateFlow<List<MovieDataModel>> = _moviesList

    init {
        getMoviesList()
    }

    fun recyclerEndReached() {
        getMoviesList()
    }

    private fun getMoviesList() {
        if (!isLoading && canLoadMore) {
            viewModelScope.launch {
                try {
                    isLoading = true
                    val movieList = getMoviesUseCase.getMoviesList(true, currentPage)

                    isLoading = false
                    canLoadMore = movieList.isNotEmpty()
                    currentPage++

                    val allMovies = ArrayList(_moviesList.value)
                    allMovies.addAll(movieList)
                    _moviesList.value = allMovies
                } catch (exception: Exception) {
                    isLoading = false
                    Log.e("getMovies", "Exception: ${exception.message}")
                }
            }
        }
    }

    fun favouriteIconClicked(movie: MovieDataModel) {
        setFavouriteMovieUseCase.setMovieIsFavourite(movie.movieID, !movie.movieLiked)
        getMoviesList()
    }
}