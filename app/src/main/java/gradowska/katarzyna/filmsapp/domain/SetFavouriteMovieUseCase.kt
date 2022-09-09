package gradowska.katarzyna.filmsapp.domain

import gradowska.katarzyna.filmsapp.data.UserDataSource

class SetFavouriteMovieUseCase(val userDataSource: UserDataSource) {

    fun setMovieIsFavourite(movieId: String, isFavourite: Boolean) =
        userDataSource.setMovieIsFavourite(movieId, isFavourite)
}