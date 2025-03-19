package gradowska.katarzyna.filmsapp.presentation.main

import android.app.Application
import gradowska.katarzyna.filmsapp.di.FilmsDI.filmModule
import gradowska.katarzyna.filmsapp.di.FilmsDI.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class FilmsApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@FilmsApplication)
            modules(filmModule, networkModule)
        }
    }
}