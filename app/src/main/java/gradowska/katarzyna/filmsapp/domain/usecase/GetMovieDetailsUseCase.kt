package gradowska.katarzyna.filmsapp.domain.usecase

import gradowska.katarzyna.filmsapp.data.MovieDataSource
import gradowska.katarzyna.filmsapp.domain.entity.MovieDetailsDataModel

class GetMovieDetailsUseCase(
    private val dataSource: MovieDataSource,
    private val getFavouriteMovieUseCase: GetFavouriteMovieUseCase,
) {

    suspend fun getMovie(id: String): MovieDetailsDataModel {
        val dataMovieModel = dataSource.getMovieFromApi(id)
            .toMovieDetailsDataModel(getFavouriteMovieUseCase.getMovieIsFavourite(id))
        return dataMovieModel
    }
}