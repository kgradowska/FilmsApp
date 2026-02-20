package gradowska.katarzyna.filmsapp.presentation

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import gradowska.katarzyna.filmsapp.domain.entity.GenreDataModel
import gradowska.katarzyna.filmsapp.domain.entity.MovieDataModel
import gradowska.katarzyna.filmsapp.presentation.filters.FiltersViewModel
import gradowska.katarzyna.filmsapp.presentation.filters.FilterScreen
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

class FilterScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val viewModel: FiltersViewModel = mockk(relaxed = true)

    private val moviesFlow = MutableStateFlow<PersistentList<MovieDataModel>>(persistentListOf())
    private val genresFlow = MutableStateFlow<PersistentList<GenreDataModel>>(persistentListOf())
    private val selectedGenreIdFlow = MutableStateFlow<Int?>(null)
    private val rangeValuesFlow = MutableStateFlow(listOf(0f, 10f))

    @Before
    fun setup() {
        stopKoin()

        every { viewModel.moviesList } returns moviesFlow
        every { viewModel.genresList } returns genresFlow
        every { viewModel.selectedGenreId } returns selectedGenreIdFlow
        every { viewModel.rangeValues } returns rangeValuesFlow

        startKoin {
            modules(module {
                single { viewModel }
            })
        }
    }

    @Test
    fun selectingGenre_ShouldTriggerViewModel() {
        val genres = persistentListOf(
            GenreDataModel(1, "Action"),
            GenreDataModel(2, "Comedy")
        )
        genresFlow.value = genres

        composeTestRule.setContent {
            FilterScreen(onMovieClick = {})
        }

        composeTestRule.onNodeWithTag("genre_text_field").performClick()

        composeTestRule.onNodeWithText("Action").performClick()

        verify { viewModel.onGenreSelected(1) }
    }

    @Test
    fun clickSearchButton_ShouldTriggerViewModelAndScroll() {
        composeTestRule.setContent {
            FilterScreen(onMovieClick = {})
        }

        composeTestRule.onNodeWithText("SEARCH").performClick()

        verify { viewModel.searchButtonClicked() }
    }

    @Test
    fun displayMovies_AndHandleClick() {
        val movies = persistentListOf(
            MovieDataModel("101", "Gladiator", "History", 9.5, "", false)
        )
        moviesFlow.value = movies

        var clickedMovieId: String? = null

        composeTestRule.setContent {
            FilterScreen(onMovieClick = { clickedMovieId = it })
        }

        composeTestRule.onNodeWithText("Gladiator").assertIsDisplayed()

        composeTestRule.onNodeWithText("Gladiator").performClick()

        assert(clickedMovieId == "101")
    }

    @Test
    fun sliderRange_ShouldShowCorrectText() {
        rangeValuesFlow.value = listOf(2.5f, 8.0f)

        composeTestRule.setContent {
            FilterScreen(onMovieClick = {})
        }

        composeTestRule.onNodeWithText("Rating: 2.5 - 8.0").assertIsDisplayed()
    }
}
