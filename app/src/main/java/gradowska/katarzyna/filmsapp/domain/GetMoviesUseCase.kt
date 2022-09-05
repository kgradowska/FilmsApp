package gradowska.katarzyna.filmsapp.domain

import gradowska.katarzyna.filmsapp.data.MovieDataSource
import gradowska.katarzyna.filmsapp.presentation.movie.MovieDataModel

class GetMoviesUseCase(val dataSource: MovieDataSource) {
     fun getMoviesList() : List<MovieDataModel>{

        var datamodel: List<MovieDataModel>
        val dataModel = dataSource.getMoviesList().map {it -> it.toMovieDataModel()}
        return dataModel
    }
}