package gradowska.katarzyna.filmsapp.data

import android.content.Context
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type


class MovieDataSource(context: Context) {

    fun getMoviesListFromJson(): List<MovieDTO> {
        val moshi: Moshi = Moshi.Builder().build()
        val listMyData: Type = Types.newParameterizedType(
            MutableList::class.java,
            MovieDTO::class.java
        )

        val jsonAdapter: JsonAdapter<List<MovieDTO>> = moshi.adapter(listMyData)

        return jsonAdapter.fromJson(jsonString) as List<MovieDTO>
    }

    private val jsonString =
        context.assets.open("moviesList.json").bufferedReader().use { it.readText() }

}