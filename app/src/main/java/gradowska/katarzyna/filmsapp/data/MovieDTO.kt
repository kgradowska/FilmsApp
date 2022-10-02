package gradowska.katarzyna.filmsapp.data

import com.squareup.moshi.JsonClass
import gradowska.katarzyna.filmsapp.presentation.movie.MovieDataModel

@JsonClass(generateAdapter = true)
class MovieDTO(
    val id: String,
    val title: String,
    val director: String,
    val score: String,
    val imageRes: String,
    val description: String,
    val releaseYear: String
) {

    fun toMovieDataModel(isFavourite: Boolean): MovieDataModel {
        val object1 = MovieDataModel(
            movieLiked = isFavourite,
            movieID = id,
            movieTitle = title,
            movieDescription = description,
            movieRate = score,
            moviePhoto = imageRes
        )
        return object1
    }
}