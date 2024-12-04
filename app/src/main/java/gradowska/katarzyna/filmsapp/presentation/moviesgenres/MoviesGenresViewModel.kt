package gradowska.katarzyna.filmsapp.presentation.moviesgenres

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gradowska.katarzyna.filmsapp.domain.entity.MovieDataModel
import gradowska.katarzyna.filmsapp.domain.usecase.GetMoviesGenresUseCase
import gradowska.katarzyna.filmsapp.domain.usecase.SetFavouriteMovieUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MoviesGenresViewModel(
    private val idGenre: Int,
    private val getMoviesGenresUseCase: GetMoviesGenresUseCase,
    private val setFavouriteMovieUseCase: SetFavouriteMovieUseCase
) : ViewModel() {

    private val _moviesList: MutableStateFlow<List<MovieDataModel>> = MutableStateFlow(listOf())
    val moviesList: StateFlow<List<MovieDataModel>> = _moviesList

    fun getMoviesList() {
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