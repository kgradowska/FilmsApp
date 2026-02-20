package gradowska.katarzyna.filmsapp.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.collections.emptySet

class UserDataSource(private val dataStore: DataStore<Preferences>) {

    suspend fun setMovieIsFavourite(movieId: String, isFavourite: Boolean) {
        dataStore.edit { preferences ->
            val currentFavourites = preferences[FAVOURITES_KEY] ?: emptySet()

            val updatedFavourites = if (isFavourite) {
                currentFavourites + movieId
            } else {
                currentFavourites - movieId
            }

            preferences[FAVOURITES_KEY] = updatedFavourites
        }
    }

    fun getMovieIsFavourite(movieId: String): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[FAVOURITES_KEY]?.contains(movieId) ?: false
        }
    }

    fun getFavouriteMovies(): Flow<List<String>> {
        return dataStore.data.map { preferences ->
            preferences[FAVOURITES_KEY]?.toList() ?: emptyList()
        }
    }

    companion object {
        private val FAVOURITES_KEY = stringSetPreferencesKey("favourite_movies")
    }
}
