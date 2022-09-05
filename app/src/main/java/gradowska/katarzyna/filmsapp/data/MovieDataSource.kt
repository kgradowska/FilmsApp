package gradowska.katarzyna.filmsapp.data

import gradowska.katarzyna.filmsapp.R


class MovieDataSource() {

    fun getMoviesList() : List<MovieDTO> {

        val m1 = MovieDTO( id = "1", title = "Wall Street", director = "Oliver Stone", score = "7,5", imageRes = R.drawable.wallstreet, description = "Młody makler giełdowy Bud Fox zostaje współpracownikiem Gordona Gekko - bogatego i bezwzględnego finansisty.", releaseYear = "1987")
        val m2 = MovieDTO( id = "2", title = "Kod Merkury", director = "Harold Becker", score = "6,8", imageRes = R.drawable.kodmerkury, description = "Agent FBI podejmuje się ochrony cierpiącego na autyzm dziewięciolatka. Chłopiec złamał tajny kod uważany za niemożliwy do rozszyfrowania i dlatego poszukują go bezwzględni zabójcy.", releaseYear = "1998")
        val m3 = MovieDTO( id = "3", title = "Dosięgnąć kosmosu", director = "Joe Johnston", score = "7,5", imageRes = R.drawable.dosiegnackosmosu, description = "Nastolatek postanawia skonstruować własną rakietę, będąc zainspirowanym wyniesieniem na orbitę pierwszego sztucznego satelity Ziemi.", releaseYear = "1999")
        val m4 = MovieDTO( id = "4", title = "Słodki listopad", director = "Pat O'Connor", score = "7,3", imageRes = R.drawable.sweetnovember, description = "Sara poznaje Nelsona, który zostaje jej chłopakiem na listopad.", releaseYear = "2001")
        val m5 = MovieDTO( id = "5", title = "Praktykant", director = "Nancy Meyers", score = "7,2", imageRes = R.drawable.praktykant, description = "Znudzony i zmęczony emeryturą wdowiec Ben postanawia zmienić swoje życie, rozpoczynając staż w sklepie internetowym z odzieżą.", releaseYear = "2015")
        val m6 = MovieDTO( id = "6", title = "Malena", director = "Giuseppe Tornatore", score = "7,3", imageRes = R.drawable.malena, description = "Jest rok 1941. Do sycylijskiego miasteczka przyjeżdża piękna Malena. 13-letni Renato Amoroso dostaje na jej punkcie obsesji.", releaseYear = "2000")
        return listOf(m1, m2, m3, m4, m5, m6)
    }
}