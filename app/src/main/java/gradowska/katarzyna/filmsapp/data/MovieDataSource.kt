package gradowska.katarzyna.filmsapp.data

import android.content.Context
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import gradowska.katarzyna.filmsapp.data.entity.MoviesListDTO
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
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

    suspend fun getMoviesListFromApi(): MoviesListDTO {
        return apiService.fetchMoviesList(apiKey)
    }

    private val jsonString =
        context.assets.open("moviesList.json").bufferedReader().use { it.readText() }
}