package gradowska.katarzyna.filmsapp.domain.usecase

import gradowska.katarzyna.filmsapp.data.MovieDataSource
import gradowska.katarzyna.filmsapp.domain.entity.MovieDataModel
import kotlinx.coroutines.flow.first

class GetSearchedMovieDetailsUseCase(
    private val dataSource: MovieDataSource,
    private val getFavouriteMovieUseCase: GetFavouriteMovieUseCase
) {

    suspend fun getSearchedMovieList(query: String, currentPage: Int): List<MovieDataModel> {
        return dataSource.getSearchedMovieFromApi(query, currentPage).results.map {
            it.toMovieDataModel(isFavourite = getFavouriteMovieUseCase(it.id.toString()).first())
        }
    }
}