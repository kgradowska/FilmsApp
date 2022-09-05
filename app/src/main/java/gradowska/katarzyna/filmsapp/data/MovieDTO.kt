package gradowska.katarzyna.filmsapp.data

import gradowska.katarzyna.filmsapp.presentation.movie.MovieDataModel

class MovieDTO(val id: String, val title: String, val director: String, val score: String, val imageRes: Int, val description: String, val releaseYear: String) {

     fun toMovieDataModel(): MovieDataModel{
        val object1 = MovieDataModel(movieID = id, movieTitle = title, movieDescription = description, movieRate = score, moviePhoto = imageRes)
        return object1
    }
}