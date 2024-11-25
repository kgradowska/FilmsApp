package gradowska.katarzyna.filmsapp.presentation.moviesgenres

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gradowska.katarzyna.filmsapp.databinding.ItemMovieBinding
import gradowska.katarzyna.filmsapp.domain.entity.MovieDataModel
import gradowska.katarzyna.filmsapp.domain.usecase.GetMoviesGenresUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.math.log

class MoviesGenresViewModel(
    private val idGenre: Int,
    private val getMoviesGenresUseCase: GetMoviesGenresUseCase
) : ViewModel() {

    private val _moviesList: MutableStateFlow<List<MovieDataModel>> = MutableStateFlow(listOf())
    val moviesList: StateFlow<List<MovieDataModel>> = _moviesList

    init {
        getMoviesList()
    }

    private fun getMoviesList() {
        viewModelScope.launch {
            val movieList = getMoviesGenresUseCase.getMovieList(
                query = null,
                currentPage = 1,
                withGenres = idGenre.toString()
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
}