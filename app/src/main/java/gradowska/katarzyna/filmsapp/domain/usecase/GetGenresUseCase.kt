package gradowska.katarzyna.filmsapp.domain.usecase

import gradowska.katarzyna.filmsapp.data.MovieDataSource
import gradowska.katarzyna.filmsapp.domain.entity.GenreDataModel

class GetGenresUseCase(private val dataSource: MovieDataSource) {

    suspend fun getGenres(): List<GenreDataModel> {
        return dataSource.getGenresFromApi().genres.map { it.toGenreDataModel() }
    }
}
