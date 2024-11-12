package gradowska.katarzyna.filmsapp.domain.usecase

import gradowska.katarzyna.filmsapp.data.MovieDataSource
import gradowska.katarzyna.filmsapp.domain.entity.MovieDataModel

class GetSearchedMovieDetailsUseCase(
    private val dataSource: MovieDataSource,
    val getFavouriteMovieUseCase: GetFavouriteMovieUseCase
) {

    suspend fun getSearchedMovieList(query: String, currentPage: Int): List<MovieDataModel> {
        return dataSource.getSearchedMovieFromApi(query, currentPage).results.map { it ->
            it.toMovieDataModel(isFavourite = getFavouriteMovieUseCase.getMovieIsFavourite(it.id.toString()))
        }
    }
}