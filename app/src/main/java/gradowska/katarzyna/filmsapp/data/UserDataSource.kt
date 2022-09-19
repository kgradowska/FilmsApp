package gradowska.katarzyna.filmsapp.data

import android.content.Context

class UserDataSource(context: Context) {
    private val sharedPref = context.getSharedPreferences("USER_PREFS", Context.MODE_PRIVATE)

    fun setMovieIsFavourite(movieId: String, isFavourite: Boolean) {
        with(sharedPref.edit()) {
            putBoolean(movieId, isFavourite)
            apply()
        }
    }

    fun getMovieIsFavourite(movieId: String): Boolean {
        return sharedPref.getBoolean(movieId, false)
    }


}