package gradowska.katarzyna.filmsapp.data.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import gradowska.katarzyna.filmsapp.domain.entity.MovieDataModel


@JsonClass(generateAdapter = true)
data class MoviesListDTO(
    @Json(name = "page")
    val page: Int,
    @Json(name = "results")
    val results: List<Result>,
    @Json(name = "total_pages")
    val totalPages: Int,
    @Json(name = "total_results")
    val totalResults: Int
) {

    @JsonClass(generateAdapter = true)
    data class Result(
        @Json(name = "adult")
        val adult: Boolean,
        @Json(name = "backdrop_path")
        val backdropPath: String?,
        @Json(name = "genre_ids")
        val genreIds: List<Int>?,
        @Json(name = "id")
        val id: Int,
        @Json(name = "original_language")
        val originalLanguage: String,
        @Json(name = "original_title")
        val originalTitle: String,
        @Json(name = "overview")
        val overview: String,
        @Json(name = "poster_path")
        val posterPath: String?,
        @Json(name = "title")
        val title: String,
        @Json(name = "vote_average")
        val voteAverage: Double?,
        @Json(name = "vote_count")
        val voteCount: Int?
    ) {
        fun toMovieDataModel(isFavourite: Boolean) = MovieDataModel(
            movieID = id.toString(),
            movieTitle = title,
            movieDescription = overview,
            moviePhoto = "https://image.tmdb.org/t/p/original/" + posterPath,
            movieLiked = isFavourite,
            movieRate = voteAverage
        )
    }
}