package gradowska.katarzyna.filmsapp.domain.entity

data class MovieDetailsDataModel(
    val id: String,
    val title: String,
    val description: String,
    val rate: String,
    val photo: String,
    val isLiked: Boolean,
    val releaseDate: String,
    val voteCount: String,
    val genres: String,
    val runtime: String,
    val budget: String,
    val revenue: String,
    val quote: String,
    val productionCountries: String,
    val originalLanguage: String,
    val originalTitle: String,
    val backdropPath: String
)
