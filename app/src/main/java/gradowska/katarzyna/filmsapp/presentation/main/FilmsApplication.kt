package gradowska.katarzyna.filmsapp.presentation.main

import android.app.Application
import gradowska.katarzyna.filmsapp.di.FilmsDI.filmModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class FilmsApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger() // funkcja wypisuje loggi z Koina
            androidContext(this@FilmsApplication) // podanie contextu
            modules(filmModule)
        }
    }
}