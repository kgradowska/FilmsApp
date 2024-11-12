package gradowska.katarzyna.filmsapp.domain.entity

data class MovieDetailsDataModel(
    val movieID: String,
    val movieTitle: String,
    val movieDescription: String,
    val movieRate: String,
    val moviePhoto: String,
    var movieLiked: Boolean,
    val movieReleaseDate: String,
    val movieVoteCount: String,
    val movieGenres: String,
    val movieRuntime: String,
    val movieBudget: String,
    val movieRevenue: String,
    val movieTagline: String,
    val movieProductionCountries: String,
    val movieOriginalLanguage: String,
    val movieOriginalTitle: String,
    val movieBackdropPath: String
) {
}