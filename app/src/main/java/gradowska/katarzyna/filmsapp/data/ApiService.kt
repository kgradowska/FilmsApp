package gradowska.katarzyna.filmsapp.data

import gradowska.katarzyna.filmsapp.data.entity.GenresDTO
import gradowska.katarzyna.filmsapp.data.entity.MovieDetailsDTO
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

    @GET("movie/{movie_id}")
    suspend fun fetchMovie(
        @Path("movie_id") movie_id: String,
        @Query("api_key") api_key: String,
    ): MovieDetailsDTO

    @GET("search/movie")
    suspend fun fetchSearchingMovie(
        @Query("api_key") api_key: String,
        @Query("query") query: String,
        @Query("page") page: Int,
    ): MoviesListDTO

    @GET("discover/movie")
    suspend fun fetchMoviesInGenre(
        @Query("api_key") api_key: String,
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("sort_by") sort_by: String,
        @Query("with_genres") with_genres: String,
    ): MoviesListDTO

    @GET("genre/movie/list")
    suspend fun fetchGenres(
        @Query("api_key") api_key: String
    ): GenresDTO
}