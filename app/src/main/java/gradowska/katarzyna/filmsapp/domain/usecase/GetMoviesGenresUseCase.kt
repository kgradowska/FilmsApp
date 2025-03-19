package gradowska.katarzyna.filmsapp.domain.usecase

import gradowska.katarzyna.filmsapp.data.MovieDataSource
import gradowska.katarzyna.filmsapp.domain.entity.MovieDataModel

class GetMoviesGenresUseCase(
    private val dataSource: MovieDataSource,
    private val getFavouriteMovieUseCase: GetFavouriteMovieUseCase
) {

    suspend fun getMovieList(
        query: String?,
        currentPage: Int?,
        sortBy: String? = "popularity.desc",
        withGenres: String?
    ): List<MovieDataModel> {
        return dataSource.getMoviesInGenre(query, currentPage, sortBy, withGenres).results.map {
            it.toMovieDataModel(isFavourite = getFavouriteMovieUseCase.getMovieIsFavourite(it.id.toString()))
        }
    }
}