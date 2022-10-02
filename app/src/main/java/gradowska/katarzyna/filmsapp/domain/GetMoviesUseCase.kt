package gradowska.katarzyna.filmsapp.domain

import gradowska.katarzyna.filmsapp.data.MovieDataSource
import gradowska.katarzyna.filmsapp.presentation.movie.MovieDataModel

class GetMoviesUseCase(
    val dataSource: MovieDataSource,
    val getFavouriteMovieUseCase: GetFavouriteMovieUseCase
) {
    fun getMoviesList(): List<MovieDataModel> {
        val dataModel = dataSource.getMoviesListFromJson().map { it ->
            it.toMovieDataModel(
                isFavourite = getFavouriteMovieUseCase.getMovieIsFavourite(it.id)
            )
        }

        return dataModel
    }
}