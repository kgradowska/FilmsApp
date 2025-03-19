package gradowska.katarzyna.filmsapp.presentation.genres

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gradowska.katarzyna.filmsapp.domain.entity.GenreDataModel
import gradowska.katarzyna.filmsapp.domain.usecase.GetGenresUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GenresFragmentViewModel(
    private val getGenresUseCase: GetGenresUseCase
) : ViewModel() {

    private val _genresList: MutableStateFlow<List<GenreDataModel>> = MutableStateFlow(listOf())
    val genresList: StateFlow<List<GenreDataModel>> = _genresList

    init {
        viewModelScope.launch {
            getGenres()
        }
    }

    private suspend fun getGenres() {
        val genres = getGenresUseCase.getGenres()
        _genresList.emit(genres)
    }
}
