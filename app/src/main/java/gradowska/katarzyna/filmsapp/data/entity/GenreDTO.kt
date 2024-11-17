package gradowska.katarzyna.filmsapp.data.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import gradowska.katarzyna.filmsapp.domain.entity.GenreDataModel

@JsonClass(generateAdapter = true)
data class GenresDTO(
    @Json(name = "genres")
    val genres: List<Genre>
) {

    @JsonClass(generateAdapter = true)
    data class Genre(
        @Json(name = "id")
        val id: Int,
        @Json(name = "name")
        val name: String,
    ) {
        fun toGenreDataModel() = GenreDataModel(
            id = id,
            name = name
        )
    }
}
