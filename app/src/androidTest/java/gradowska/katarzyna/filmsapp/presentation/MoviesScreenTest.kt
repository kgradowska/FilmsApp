package gradowska.katarzyna.filmsapp.presentation

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.activity.ComponentActivity
import gradowska.katarzyna.filmsapp.domain.entity.MovieDataModel
import gradowska.katarzyna.filmsapp.presentation.searchMovies.SearchMoviesViewModel
import gradowska.katarzyna.filmsapp.presentation.searchMovies.MoviesScreen
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import gradowska.katarzyna.filmsapp.R

class MoviesScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val viewModel: SearchMoviesViewModel = mockk(relaxed = true)
    private val moviesFlow = MutableStateFlow<List<MovieDataModel>>(emptyList())
    private val toastFlow = MutableSharedFlow<Unit>()

    @Before
    fun setup() {
        stopKoin()
        every { viewModel.moviesList } returns moviesFlow
        every { viewModel.showToast } returns toastFlow

        startKoin {
            modules(module {
                single { viewModel }
            })
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun searchField_ShouldUpdateText_AndClearOnButtonClick() {
        val context = composeTestRule.activity
        val searchPlaceholder = context.getString(R.string.search_movie)
        val clearSearch = context.getString(R.string.clear_search)


        composeTestRule.setContent {
            MoviesScreen(onMovieClick = {})
        }

        composeTestRule.onNodeWithText(searchPlaceholder).performTextInput("Batman")

        composeTestRule.onNodeWithText("Batman").assertIsDisplayed()

        composeTestRule.onNodeWithContentDescription(clearSearch).performClick()

        composeTestRule.onNodeWithText("Batman").assertDoesNotExist()
    }

    @Test
    fun clickingSearchOnKeyboard_ShouldTriggerViewModel() {
        val context = composeTestRule.activity
        val searchPlaceholder = context.getString(R.string.search_movie)

        composeTestRule.setContent {
            MoviesScreen(onMovieClick = {})
        }

        composeTestRule.onNodeWithText(searchPlaceholder).performTextInput("Joker")

        composeTestRule.onNodeWithText("Joker").performImeAction()

        verify { viewModel.searchClicked("Joker") }
    }

    @Test
    fun list_ShouldDisplayMovies_AndHandleClicks() {
        val movies = listOf(
            MovieDataModel("1", "Inception", "Desc", 9.0, "", false),
            MovieDataModel("2", "Interstellar", "Desc", 8.5, "", false)
        )
        moviesFlow.value = movies

        var clickedMovieId: String? = null

        composeTestRule.setContent {
            MoviesScreen(onMovieClick = { clickedMovieId = it })
        }

        composeTestRule.onNodeWithText("Inception").assertIsDisplayed()
        composeTestRule.onNodeWithText("Interstellar").assertIsDisplayed()

        composeTestRule.onNodeWithText("Inception").performClick()

        assert(clickedMovieId == "1")
    }
}
