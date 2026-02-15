package gradowska.katarzyna.filmsapp.domain.usecase

import gradowska.katarzyna.filmsapp.data.MovieDataSource
import gradowska.katarzyna.filmsapp.domain.entity.MovieDataModel
import kotlinx.coroutines.flow.first

class GetMoviesUseCase(
    private val dataSource: MovieDataSource,
    private val getFavouriteMovieUseCase: GetFavouriteMovieUseCase
) {
    suspend fun getMoviesList(loadFromApi: Boolean, page: Int): List<MovieDataModel> {
        val dataModel = if (loadFromApi) {
            dataSource.getMoviesListFromApi(page).results.map {
                it.toMovieDataModel(isFavourite = getFavouriteMovieUseCase(it.id.toString()).first())
            }
        } else {
            dataSource.getMoviesListFromJson().map {
                it.toMovieDataModel(
                    isFavourite = getFavouriteMovieUseCase(it.id).first()
                )
            }
        }
        return dataModel
    }
}
