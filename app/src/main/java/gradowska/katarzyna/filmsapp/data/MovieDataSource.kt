package gradowska.katarzyna.filmsapp.data

import android.content.Context
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import gradowska.katarzyna.filmsapp.data.entity.GenresDTO
import gradowska.katarzyna.filmsapp.data.entity.MovieDetailsDTO
import gradowska.katarzyna.filmsapp.data.entity.MoviesListDTO
import java.lang.reflect.Type

class MovieDataSource(
    context: Context,
    private val apiService: ApiService,
    private val moshi: Moshi,
    private val apiKey: String,
) {

    fun getMoviesListFromJson(): List<MovieDTO> {
        val listMyData: Type = Types.newParameterizedType(
            MutableList::class.java,
            MovieDTO::class.java
        )

        val jsonAdapter: JsonAdapter<List<MovieDTO>> = moshi.adapter(listMyData)

        return jsonAdapter.fromJson(jsonString) as List<MovieDTO>
    }

    suspend fun getMoviesListFromApi(page: Int): MoviesListDTO {
        return apiService.fetchMoviesList(apiKey, page)
    }

    suspend fun getMovieFromApi(id: String): MovieDetailsDTO {
        return apiService.fetchMovie(id, apiKey)
    }

    suspend fun getSearchedMovieFromApi(query: String, page: Int): MoviesListDTO {
        return apiService.fetchSearchingMovie(apiKey, query, page)
    }

    suspend fun getMoviesInGenre(
        query: String,
        page: Int,
        sortBy: String,
        withGenres: String
    ): MoviesListDTO {
        return apiService.fetchMoviesInGenre(apiKey, query, page, sortBy, withGenres)
    }

    suspend fun getGenresFromApi(): GenresDTO {
        return apiService.fetchGenres(apiKey)
    }

    private val jsonString =
        context.assets.open("moviesList.json").bufferedReader().use { it.readText() }
}
