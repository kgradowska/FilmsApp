package gradowska.katarzyna.filmsapp.di

import gradowska.katarzyna.filmsapp.data.MovieDataSource
import gradowska.katarzyna.filmsapp.data.UserDataSource
import gradowska.katarzyna.filmsapp.domain.usecase.GetFavouriteMovieUseCase
import gradowska.katarzyna.filmsapp.domain.usecase.GetMoviesUseCase
import gradowska.katarzyna.filmsapp.domain.usecase.SetFavouriteMovieUseCase
import gradowska.katarzyna.filmsapp.presentation.main.MainActivityViewModel
import gradowska.katarzyna.filmsapp.presentation.recyclerList.MoviesFragmentViewModel
import gradowska.katarzyna.filmsapp.presentation.singleMovie.SingleMovieViewModel
import gradowska.katarzyna.filmsapp.presentation.start.StartFragmentViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object FilmsDI {
    val filmModule = module {
        single { MovieDataSource(androidContext()) }
        single { UserDataSource(androidContext()) }

        factory { GetFavouriteMovieUseCase(get()) }
        factory { GetMoviesUseCase(get(), get()) }
        factory { SetFavouriteMovieUseCase(get()) }

        viewModel { MoviesFragmentViewModel(get(), get()) }
        viewModel { SingleMovieViewModel() }
        viewModel { StartFragmentViewModel() }
        viewModel { MainActivityViewModel() }
    }
}