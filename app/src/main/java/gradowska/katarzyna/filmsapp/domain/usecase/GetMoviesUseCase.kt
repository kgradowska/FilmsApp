package gradowska.katarzyna.filmsapp.domain.usecase

import gradowska.katarzyna.filmsapp.data.MovieDataSource
import gradowska.katarzyna.filmsapp.domain.entity.MovieDataModel

class GetMoviesUseCase(
    private val dataSource: MovieDataSource,
    private val getFavouriteMovieUseCase: GetFavouriteMovieUseCase
) {
    suspend fun getMoviesList(loadFromApi: Boolean, page: Int): List<MovieDataModel> {
        val dataModel = if (loadFromApi) {
            dataSource.getMoviesListFromApi(page).results.map {
                it.toMovieDataModel(isFavourite = getFavouriteMovieUseCase.getMovieIsFavourite(it.id.toString()))
            }
        } else {
            dataSource.getMoviesListFromJson().map {
                it.toMovieDataModel(
                    isFavourite = getFavouriteMovieUseCase.getMovieIsFavourite(it.id)
                )
            }
        }
        return dataModel
    }
}
