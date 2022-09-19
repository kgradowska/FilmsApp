package gradowska.katarzyna.filmsapp.domain

import gradowska.katarzyna.filmsapp.data.UserDataSource

class GetFavouriteMovieUseCase(val userDataSource: UserDataSource) {

    fun getMovieIsFavourite(movieId: String): Boolean = userDataSource.getMovieIsFavourite(movieId)
}