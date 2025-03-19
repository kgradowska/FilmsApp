package gradowska.katarzyna.filmsapp.domain.usecase

import gradowska.katarzyna.filmsapp.data.UserDataSource

class GetFavouriteMovieUseCase(private val userDataSource: UserDataSource) {

    fun getMovieIsFavourite(movieId: String): Boolean = userDataSource.getMovieIsFavourite(movieId)
}
