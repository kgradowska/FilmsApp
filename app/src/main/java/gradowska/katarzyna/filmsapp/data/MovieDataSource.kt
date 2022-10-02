package gradowska.katarzyna.filmsapp.data

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type


class MovieDataSource() {

    fun getMoviesListFromJson(): List<MovieDTO> {
        val moshi: Moshi = Moshi.Builder().build()
        val listMyData: Type = Types.newParameterizedType(
            MutableList::class.java,
            MovieDTO::class.java
        )

        val jsonAdapter: JsonAdapter<List<MovieDTO>> = moshi.adapter(listMyData)

        return jsonAdapter.fromJson(json) as List<MovieDTO>
    }

    private val json = "[\n" +
            "    {\n" +
            "      \"id\": \"1\",\n" +
            "      \"title\": \"Wall Street\",\n" +
            "      \"director\": \"Oliver Stone\",\n" +
            "      \"score\": \"7,5\",\n" +
            "      \"imageRes\": \"https://filmowo.net/wp-content/uploads/2010/09/wall-street_1987.jpg\",\n" +
            "      \"description\": \"Młody makler giełdowy Bud Fox zostaje współpracownikiem Gordona Gekko - bogatego i bezwzględnego finansisty.\",\n" +
            "      \"releaseYear\": \"1987\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"2\",\n" +
            "      \"title\": \"Kod Merkury\",\n" +
            "      \"director\": \"Harold Becker\",\n" +
            "      \"score\": \"6,8\",\n" +
            "      \"imageRes\": \"https://fwcdn.pl/fpo/00/84/84/7985867.6.jpg\",\n" +
            "      \"description\": \"Agent FBI podejmuje się ochrony cierpiącego na autyzm dziewięciolatka. Chłopiec złamał tajny kod uważany za niemożliwy do rozszyfrowania i dlatego poszukują go bezwzględni zabójcy.\",\n" +
            "      \"releaseYear\": \"1998\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"3\",\n" +
            "      \"title\": \"Dosięgnąć kosmosu\",\n" +
            "      \"director\": \"Joe Johnston\",\n" +
            "      \"score\": \"7,5\",\n" +
            "      \"imageRes\": \"https://fwcdn.pl/fpo/82/67/8267/6922331.6.jpg\",\n" +
            "      \"description\": \"Nastolatek postanawia skonstruować własną rakietę, będąc zainspirowanym wyniesieniem na orbitę pierwszego sztucznego satelity Ziemi.\",\n" +
            "      \"releaseYear\": \"1999\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"4\",\n" +
            "      \"title\": \"Słodki listopad\",\n" +
            "      \"director\": \"Pat O'Connor\",\n" +
            "      \"score\": \"7,3\",\n" +
            "      \"imageRes\": \"https://fwcdn.pl/fpo/99/13/9913/6942810.6.jpg\",\n" +
            "      \"description\": \"Sara poznaje Nelsona, który zostaje jej chłopakiem na listopad.\",\n" +
            "      \"releaseYear\": \"2001\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"5\",\n" +
            "      \"title\": \"Praktykant\",\n" +
            "      \"director\": \"Nancy Meyers\",\n" +
            "      \"score\": \"7,2\",\n" +
            "      \"imageRes\": \"https://fwcdn.pl/fpo/40/26/704026/7702551.3.jpg\",\n" +
            "      \"description\": \"Znudzony i zmęczony emeryturą wdowiec Ben postanawia zmienić swoje życie, rozpoczynając staż w sklepie internetowym z odzieżą.\",\n" +
            "      \"releaseYear\": \"2015\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"6\",\n" +
            "      \"title\": \"Malena\",\n" +
            "      \"director\": \"Giuseppe Tornatore\",\n" +
            "      \"score\": \"7,3\",\n" +
            "      \"imageRes\": \"https://fwcdn.pl/fpo/03/88/30388/7519149.3.jpg\",\n" +
            "      \"description\": \"Jest rok 1941. Do sycylijskiego miasteczka przyjeżdża piękna Malena. 13-letni Renato Amoroso dostaje na jej punkcie obsesji.\",\n" +
            "      \"releaseYear\": \"2000\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"6\",\n" +
            "      \"title\": \"Malena\",\n" +
            "      \"director\": \"Giuseppe Tornatore\",\n" +
            "      \"score\": \"7,3\",\n" +
            "      \"imageRes\": \"https://fwcdn.pl/fpo/03/88/30388/7519149.3.jpg\",\n" +
            "      \"description\": \"Jest rok 1941. Do sycylijskiego miasteczka przyjeżdża piękna Malena. 13-letni Renato Amoroso dostaje na jej punkcie obsesji.\",\n" +
            "      \"releaseYear\": \"2000\"\n" +
            "    }\n" +
            "  ]"
}