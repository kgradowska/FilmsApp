package gradowska.katarzyna.filmsapp.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import gradowska.katarzyna.filmsapp.data.ApiService
import gradowska.katarzyna.filmsapp.data.MovieDataSource
import gradowska.katarzyna.filmsapp.data.UserDataSource
import gradowska.katarzyna.filmsapp.domain.usecase.*
import gradowska.katarzyna.filmsapp.presentation.genres.GenresFragmentViewModel
import gradowska.katarzyna.filmsapp.presentation.main.MainActivityViewModel
import gradowska.katarzyna.filmsapp.presentation.moviesgenres.MoviesGenresViewModel
import gradowska.katarzyna.filmsapp.presentation.recyclerList.MoviesViewModel
import gradowska.katarzyna.filmsapp.presentation.singleMovie.SingleMovieViewModel
import gradowska.katarzyna.filmsapp.presentation.start.StartFragmentViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.*
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object FilmsDI {
    val filmModule = module {
        single { MovieDataSource(androidContext(), get(), get(), API_KEY) }
        single { UserDataSource(androidContext()) }
        single { get<Retrofit>().create(ApiService::class.java) }

        factory { GetFavouriteMovieUseCase(get()) }
        factory { GetMoviesUseCase(get(), get()) }
        factory { SetFavouriteMovieUseCase(get()) }
        factory { GetMovieDetailsUseCase(get(), get()) }
        factory { GetSearchedMovieDetailsUseCase(get(), get()) }
        factory { GetGenresUseCase(get()) }
        factory { GetMoviesGenresUseCase(get(), get()) }

        viewModel { MoviesViewModel(get(), get(), get()) }
        viewModel { (movieId: String) -> SingleMovieViewModel(movieId, get(), get()) }
        viewModel { StartFragmentViewModel() }
        viewModel { MainActivityViewModel() }
        viewModel { GenresFragmentViewModel(get()) }
        viewModel { (idGenre: Int) -> MoviesGenresViewModel(idGenre, get(), get()) }
    }

    val networkModule = module {
        single {
            Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        }

        single {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            OkHttpClient.Builder().addNetworkInterceptor(logging).build()
        }

        single {
            Retrofit.Builder()
                .client(get())
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(get()))
                .build()
        }
    }

    private val BASE_URL = "https://api.themoviedb.org/3/"
    private val API_KEY =
        "4cbf330fd2ec208655bc806eacdf5273" // generate your own api key here: https://developers.themoviedb.org/3/getting-started/introduction

}