package gradowska.katarzyna.filmsapp.domain.usecase

import gradowska.katarzyna.filmsapp.data.UserDataSource

class SetFavouriteMovieUseCase(private val userDataSource: UserDataSource) {

    fun setMovieIsFavourite(movieId: String, isFavourite: Boolean) =
        userDataSource.setMovieIsFavourite(movieId, isFavourite)
}