package gradowska.katarzyna.filmsapp.domain.usecase

import gradowska.katarzyna.filmsapp.data.UserDataSource
import kotlinx.coroutines.flow.Flow

class GetFavouriteMovieUseCase(private val userDataSource: UserDataSource) {

    operator fun invoke(movieId: String): Flow<Boolean> {
        return userDataSource.getMovieIsFavourite(movieId)
    }
}