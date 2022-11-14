package gradowska.katarzyna.filmsapp.data

import gradowska.katarzyna.filmsapp.data.entity.MoviesListDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/top_rated")
    suspend fun fetchMoviesList(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): MoviesListDTO
}