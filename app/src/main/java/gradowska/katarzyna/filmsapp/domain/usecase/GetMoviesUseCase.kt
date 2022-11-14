package gradowska.katarzyna.filmsapp.domain.usecase

import gradowska.katarzyna.filmsapp.data.MovieDataSource
import gradowska.katarzyna.filmsapp.domain.entity.MovieDataModel

class GetMoviesUseCase(
    val dataSource: MovieDataSource,
    val getFavouriteMovieUseCase: GetFavouriteMovieUseCase
) {
    suspend fun getMoviesList(loadFromApi: Boolean): List<MovieDataModel> {
        val dataModel = if (loadFromApi) {
            dataSource.getMoviesListFromApi().results.map { it ->
                it.toMovieDataModel(isFavourite = getFavouriteMovieUseCase.getMovieIsFavourite(it.id.toString()))
            }
        } else {
            dataSource.getMoviesListFromJson().map { it ->
                it.toMovieDataModel(
                    isFavourite = getFavouriteMovieUseCase.getMovieIsFavourite(it.id)
                )
            }
        }

        return dataModel
    }
}