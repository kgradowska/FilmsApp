package gradowska.katarzyna.filmsapp.data


class MovieDataSource() {

    fun getMoviesList() : List<MovieDTO> {

        val m1 = MovieDTO(
            id = "1",
            title = "Wall Street",
            director = "Oliver Stone",
            score = "7,5",
            imageRes = "https://filmowo.net/wp-content/uploads/2010/09/wall-street_1987.jpg",
            description = "Młody makler giełdowy Bud Fox zostaje współpracownikiem Gordona Gekko - bogatego i bezwzględnego finansisty.",
            releaseYear = "1987"
        )
        val m2 = MovieDTO(
            id = "2",
            title = "Kod Merkury",
            director = "Harold Becker",
            score = "6,8",
            imageRes = "https://fwcdn.pl/fpo/00/84/84/7985867.6.jpg",
            description = "Agent FBI podejmuje się ochrony cierpiącego na autyzm dziewięciolatka. Chłopiec złamał tajny kod uważany za niemożliwy do rozszyfrowania i dlatego poszukują go bezwzględni zabójcy.",
            releaseYear = "1998"
        )
        val m3 = MovieDTO(
            id = "3",
            title = "Dosięgnąć kosmosu",
            director = "Joe Johnston",
            score = "7,5",
            imageRes = "https://fwcdn.pl/fpo/82/67/8267/6922331.6.jpg",
            description = "Nastolatek postanawia skonstruować własną rakietę, będąc zainspirowanym wyniesieniem na orbitę pierwszego sztucznego satelity Ziemi.",
            releaseYear = "1999"
        )
        val m4 = MovieDTO(
            id = "4",
            title = "Słodki listopad",
            director = "Pat O'Connor",
            score = "7,3",
            imageRes = "https://fwcdn.pl/fpo/99/13/9913/6942810.6.jpg",
            description = "Sara poznaje Nelsona, który zostaje jej chłopakiem na listopad.",
            releaseYear = "2001"
        )
        val m5 = MovieDTO(
            id = "5",
            title = "Praktykant",
            director = "Nancy Meyers",
            score = "7,2",
            imageRes = "https://fwcdn.pl/fpo/40/26/704026/7702551.3.jpg",
            description = "Znudzony i zmęczony emeryturą wdowiec Ben postanawia zmienić swoje życie, rozpoczynając staż w sklepie internetowym z odzieżą.",
            releaseYear = "2015"
        )
        val m6 = MovieDTO(
            id = "6",
            title = "Malena",
            director = "Giuseppe Tornatore",
            score = "7,3",
            imageRes = "https://fwcdn.pl/fpo/03/88/30388/7519149.3.jpg",
            description = "Jest rok 1941. Do sycylijskiego miasteczka przyjeżdża piękna Malena. 13-letni Renato Amoroso dostaje na jej punkcie obsesji.",
            releaseYear = "2000"
        )
        val m7 = MovieDTO(
            id = "7",
            title = "Malena7777",
            director = "Giuseppe Tornatore",
            score = "7,3",
            imageRes = "https://fwcdn.pl/fpo/03/88/30388/7519149.3.jpg",
            description = "Jest rok 1941. Do sycylijskiego miasteczka przyjeżdża piękna Malena. 13-letni Renato Amoroso dostaje na jej punkcie obsesji.",
            releaseYear = "2000"
        )
        val m8 = MovieDTO(
            id = "8",
            title = "Malena88888",
            director = "Giuseppe Tornatore",
            score = "7,3",
            imageRes = "https://fwcdn.pl/fpo/03/88/30388/7519149.3.jpg",
            description = "Jest rok 1941. Do sycylijskiego miasteczka przyjeżdża piękna Malena. 13-letni Renato Amoroso dostaje na jej punkcie obsesji.",
            releaseYear = "2000"
        )
        val m9 = MovieDTO(
            id = "9",
            title = "Malena99999",
            director = "Giuseppe Tornatore",
            score = "7,3",
            imageRes = "https://fwcdn.pl/fpo/03/88/30388/7519149.3.jpg",
            description = "Jest rok 1941. Do sycylijskiego miasteczka przyjeżdża piękna Malena. 13-letni Renato Amoroso dostaje na jej punkcie obsesji.",
            releaseYear = "2000"
        )
        val m10 = MovieDTO(
            id = "10",
            title = "Malena10",
            director = "Giuseppe Tornatore",
            score = "7,3",
            imageRes = "https://fwcdn.pl/fpo/03/88/30388/7519149.3.jpg",
            description = "Jest rok 1941. Do sycylijskiego miasteczka przyjeżdża piękna Malena. 13-letni Renato Amoroso dostaje na jej punkcie obsesji.",
            releaseYear = "2000"
        )
        val m11 = MovieDTO(
            id = "11",
            title = "Malena11",
            director = "Giuseppe Tornatore",
            score = "7,3",
            imageRes = "https://fwcdn.pl/fpo/03/88/30388/7519149.3.jpg",
            description = "Jest rok 1941. Do sycylijskiego miasteczka przyjeżdża piękna Malena. 13-letni Renato Amoroso dostaje na jej punkcie obsesji.",
            releaseYear = "2000"
        )
        return listOf(m1, m2, m3, m4, m5, m6, m7, m8, m9, m10, m11)
    }
}