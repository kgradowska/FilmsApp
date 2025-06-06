package gradowska.katarzyna.filmsapp.domain.entity

data class MovieDataModel(
    val movieID: String,
    val movieTitle: String,
    val movieDescription: String,
    val movieRate: Double?,
    val moviePhoto: String,
    var movieLiked: Boolean
)
