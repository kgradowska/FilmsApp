package gradowska.katarzyna.filmsapp.presentation.movie

data class MovieDataModel(
    val movieID: String,
    val movieTitle: String,
    val movieDescription: String,
    val movieRate: String,
    val moviePhoto: String,
    var movieLiked: Boolean
)
