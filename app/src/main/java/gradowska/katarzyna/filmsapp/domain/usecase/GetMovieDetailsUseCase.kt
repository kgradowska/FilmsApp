package gradowska.katarzyna.filmsapp.domain.usecase

import gradowska.katarzyna.filmsapp.data.MovieDataSource
import gradowska.katarzyna.filmsapp.domain.entity.MovieDetailsDataModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class GetMovieDetailsUseCase(
    private val dataSource: MovieDataSource,
    private val getFavouriteMoviesUseCase: GetFavouriteMoviesUseCase,
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getMovie(id: String): Flow<MovieDetailsDataModel> {
        return flow {
            val movie = dataSource.getMovieFromApi(id)
            emit(movie)
        }.flatMapLatest { movie ->
            getFavouriteMoviesUseCase().map { favourites ->
                movie.toMovieDetailsDataModel(
                    favourites.contains(movie.id.toString())
                )
            }
        }
    }
}
