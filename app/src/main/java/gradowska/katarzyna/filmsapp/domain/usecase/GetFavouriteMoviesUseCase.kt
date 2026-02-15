package gradowska.katarzyna.filmsapp.domain.usecase

import gradowska.katarzyna.filmsapp.data.UserDataSource
import kotlinx.coroutines.flow.Flow

class GetFavouriteMoviesUseCase(private val userDataSource: UserDataSource) {

    operator fun invoke(): Flow<List<String>> {
        return userDataSource.getFavouriteMovies()
    }
}
