package gradowska.katarzyna.filmsapp.presentation.recyclerList

import androidx.lifecycle.ViewModel
import gradowska.katarzyna.filmsapp.R
import gradowska.katarzyna.filmsapp.data.MovieDataSource
import gradowska.katarzyna.filmsapp.domain.GetMoviesUseCase
import gradowska.katarzyna.filmsapp.presentation.movie.MovieDataModel

class MoviesFragmentViewModel : ViewModel() {
    fun getFormat() : List<MovieDataModel>{
        /*val movie1 = MovieDataModel("1", "Tajemnice Los Angeles", "Los Angeles, rok 1953. Trzech działających niezależnie od siebie funkcjonariuszy policji próbuje rozwiązać sprawę wielokrotnego zabójstwa w nocnym barze.","7,7", R.drawable.los_angeles)
        val movie2 = MovieDataModel("2","Jeździec bez głowy", "Nowojorski policjant przybywa do małej osady, by rozwiązać sprawę tajemniczych morderstw dokonywanych rzekomo przez jeźdźca bez głowy.", "7,4", R.drawable.jezdziec)
        val movie3 = MovieDataModel("3","Forrest Gump", "Historia życia Forresta, chłopca o niskim ilorazie inteligencji z niedowładem kończyn, który staje się miliarderem i bohaterem wojny w Wietnamie.", "8,5",R.drawable.forrest)
        val movie4 = MovieDataModel("4","Żywot Briana", "Brian, rówieśnik Chrystusa, przychodzi na świat w Betlejem i zostaje omyłkowo uznany za Mesjasza.", "7,9", R.drawable.zywotbriana)
        val movie5 = MovieDataModel("5","Gran Torino", "Walt Kowalski to emerytowany weteran żyjący we własnym poukładanym świecie. Jego spokój zostaje zburzony przez nowych sąsiadów z Azji, których syn spróbuje ukraść mu ulubione auto.", "8,2", R.drawable.gantorino)
        val movie6 = MovieDataModel("6","Lęk pierwotny", "Martin zostaje adwokatem młodego Aarona oskarżonego o morderstwo arcybiskupa.", "7,9", R.drawable.lekpierwotny)
        val movie7 = MovieDataModel("7","Czekolada", "Matka i córka przyjeżdżają do małego miasteczka, gdzie otwierają sklep, w ofercie którego znaleźć można czekoladę pod każdą niemal postacią. Przedsięwzięcie spotyka się z dezaprobatą burmistrza.", "7,5", R.drawable.czekolada)
        val movie8 = MovieDataModel("8","Rodzina Addamsów", "W mrocznym domu Addamsów pojawia się zaginiony przed laty członek rodziny, wujek Fester.", "7,3", R.drawable.rodzinaaddamsow)
        return listOf(movie1, movie2, movie3, movie4, movie5, movie6, movie7, movie8)*/
        val val1 = GetMoviesUseCase(MovieDataSource()).getMoviesList()
        return val1
    }
}