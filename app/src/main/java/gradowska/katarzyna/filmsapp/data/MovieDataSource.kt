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


class MovieDataSource(context: Context) {

    val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    private val BASE_URL = "https://api.themoviedb.org/3/"
    private val API_KEY =
        "" // generate your own api key here: https://developers.themoviedb.org/3/getting-started/introduction

    private val retrofit: Retrofit by lazy {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        Retrofit.Builder()
            .client(OkHttpClient.Builder().addNetworkInterceptor(logging).build())
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    fun getMoviesListFromJson(): List<MovieDTO> {
        val listMyData: Type = Types.newParameterizedType(
            MutableList::class.java,
            MovieDTO::class.java
        )

        val jsonAdapter: JsonAdapter<List<MovieDTO>> = moshi.adapter(listMyData)

        return jsonAdapter.fromJson(jsonString) as List<MovieDTO>
    }

    suspend fun getMoviesListFromApi(): MoviesListDTO {
        return apiService.fetchMoviesList(API_KEY)
    }

    private val jsonString =
        context.assets.open("moviesList.json").bufferedReader().use { it.readText() }
}