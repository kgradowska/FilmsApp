package gradowska.katarzyna.filmsapp.presentation.recyclerList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import gradowska.katarzyna.filmsapp.R
import gradowska.katarzyna.filmsapp.data.MovieDataSource
import gradowska.katarzyna.filmsapp.data.UserDataSource
import gradowska.katarzyna.filmsapp.domain.GetMoviesUseCase
import gradowska.katarzyna.filmsapp.domain.SetFavouriteMovieUseCase
import gradowska.katarzyna.filmsapp.presentation.movie.MovieDataModel

class MoviesFragmentViewModel(application: Application) : AndroidViewModel(application) {
    private val setFavouriteMovieUseCase: SetFavouriteMovieUseCase =
        SetFavouriteMovieUseCase(UserDataSource(context = getApplication<Application>().applicationContext))
    private val movieList = GetMoviesUseCase(MovieDataSource()).getMoviesList()

    fun getMoviesList(): List<MovieDataModel> {
        return movieList
    }

    fun favouriteIconClicked(movie: MovieDataModel) {
        for (m in movieList) {
            if (m.movieID == movie.movieID) {
                m.movieLiked = !m.movieLiked
                setFavouriteMovieUseCase.setMovieIsFavourite(m.movieID, m.movieLiked)
            }
        }
    }
}