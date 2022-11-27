package gradowska.katarzyna.filmsapp.data.entity

import gradowska.katarzyna.filmsapp.domain.entity.MovieDetailsDataModel
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json


// todo cleanup - remove unused field
@JsonClass(generateAdapter = true)
data class MovieDetailsDTO(
    @Json(name = "adult")
    val adult: Boolean,
    @Json(name = "backdrop_path")
    val backdropPath: String?,
    @Json(name = "belongs_to_collection")
    val belongsToCollection: BelongsToCollection?,
    @Json(name = "budget")
    val budget: Int,
    @Json(name = "genres")
    val genres: List<Genre>,
    @Json(name = "homepage")
    val homepage: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "imdb_id")
    val imdbId: String,
    @Json(name = "original_language")
    val originalLanguage: String,
    @Json(name = "original_title")
    val originalTitle: String,
    @Json(name = "overview")
    val overview: String,
    @Json(name = "popularity")
    val popularity: Double,
    @Json(name = "poster_path")
    val posterPath: String,
    @Json(name = "production_companies")
    val productionCompanies: List<ProductionCompany>,
    @Json(name = "production_countries")
    val productionCountries: List<ProductionCountry>,
    @Json(name = "release_date")
    val releaseDate: String,
    @Json(name = "revenue")
    val revenue: Int,
    @Json(name = "runtime")
    val runtime: Int?,
    @Json(name = "spoken_languages")
    val spokenLanguages: List<SpokenLanguage>,
    @Json(name = "status")
    val status: String,
    @Json(name = "tagline")
    val tagline: String?,
    @Json(name = "title")
    val title: String,
    @Json(name = "video")
    val video: Boolean,
    @Json(name = "vote_average")
    val voteAverage: Double,
    @Json(name = "vote_count")
    val voteCount: Int
) {

    private fun createGenresText(): String {
        var genresText = ""
        var counter = 0
        for (item in genres) {
            if (counter < 4) {
                if (counter > 0) {
                    genresText += "  ·  "
                }
                genresText += item.name
                counter++
            }
        }
        return genresText
    }

    private fun createProductionCountriesText(): String {
        var productionCountriesText = ""
        var counter = 0
        for (item in productionCountries) {
            if (counter < 4) {
                if (counter > 0) {
                    productionCountriesText += "\n"
                }
                productionCountriesText += "- " + item.name
                counter++
            }
        }
        return productionCountriesText
    }

    fun toMovieDetailsDataModel(isFavourite: Boolean) = MovieDetailsDataModel(
        movieID = id.toString(),
        movieTitle = title,
        movieDescription = overview,
        moviePhoto = "https://image.tmdb.org/t/p/original/" + posterPath,
        movieLiked = isFavourite,
        movieRate = voteAverage.toString(),
        movieReleaseDate = "   " + releaseDate,
        movieVoteCount = voteCount.toString(),
        movieGenres = createGenresText(),
        movieRuntime = runtime.toString() + " min",
        movieBudget = budget.toString() + " $",
        movieRevenue = revenue.toString() + " $",
        movieOriginalLanguage = originalLanguage,
        movieOriginalTitle = originalTitle,
        movieTagline = "  " + tagline + "  ",
        movieBackdropPath = "https://image.tmdb.org/t/p/original/" + backdropPath,
        movieProductionCountries = createProductionCountriesText()
    )
}

@JsonClass(generateAdapter = true)
data class BelongsToCollection(
    @Json(name = "backdrop_path")
    val backdropPath: String?,
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "poster_path")
    val posterPath: String
)

@JsonClass(generateAdapter = true)
data class Genre(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String
)

@JsonClass(generateAdapter = true)
data class ProductionCompany(
    @Json(name = "id")
    val id: Int,
    @Json(name = "logo_path")
    val logoPath: String?,
    @Json(name = "name")
    val name: String,
    @Json(name = "origin_country")
    val originCountry: String
)

@JsonClass(generateAdapter = true)
data class ProductionCountry(
    @Json(name = "iso_3166_1")
    val iso31661: String,
    @Json(name = "name")
    val name: String
)

@JsonClass(generateAdapter = true)
data class SpokenLanguage(
    @Json(name = "english_name")
    val englishName: String,
    @Json(name = "iso_639_1")
    val iso6391: String,
    @Json(name = "name")
    val name: String
)