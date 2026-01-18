package gradowska.katarzyna.filmsapp.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
object MoviesHome

@Serializable
object MoviesFilter

@Serializable
data class MovieDetails(val movieId: Int)