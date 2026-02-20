package gradowska.katarzyna.filmsapp.data.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import gradowska.katarzyna.filmsapp.domain.entity.MovieDetailsDataModel

@JsonClass(generateAdapter = true)
data class MovieDetailsDTO(
    @Json(name = "backdrop_path")
    val backdropPath: String?,
    @Json(name = "budget")
    val budget: Int,
    @Json(name = "genres")
    val genres: List<Genre>,
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
        var addedString = ""
        if (productionCountries.size > 1) {
            addedString = "- "
        }
        for (item in productionCountries) {
            if (counter < 4) {
                if (counter > 0) {
                    productionCountriesText += "\n"
                }
                productionCountriesText += addedString + item.name
                counter++
            }
        }
        return productionCountriesText
    }

    fun toMovieDetailsDataModel(isFavourite: Boolean) = MovieDetailsDataModel(
        id = id.toString(),
        title = title,
        description = overview,
        photo = "https://image.tmdb.org/t/p/original/" + posterPath,
        isLiked = isFavourite,
        rate = voteAverage.toString(),
        releaseDate = "   " + releaseDate,
        voteCount = voteCount.toString(),
        genres = createGenresText(),
        runtime = runtime.toString() + " min",
        budget = budget.toString() + " $",
        revenue = revenue.toString() + " $",
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        quote = "  " + tagline + "  ",
        backdropPath = "https://image.tmdb.org/t/p/original/" + backdropPath,
        productionCountries = createProductionCountriesText()
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
    val posterPath: String?
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