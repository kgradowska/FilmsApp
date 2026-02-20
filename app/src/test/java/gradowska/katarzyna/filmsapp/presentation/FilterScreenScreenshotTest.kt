package gradowska.katarzyna.filmsapp.presentation

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onRoot
import com.github.takahirom.roborazzi.captureRoboImage
import gradowska.katarzyna.filmsapp.domain.entity.GenreDataModel
import gradowska.katarzyna.filmsapp.domain.entity.MovieDataModel
import gradowska.katarzyna.filmsapp.presentation.filters.FiltersViewModel
import gradowska.katarzyna.filmsapp.presentation.filters.FilterScreen
import gradowska.katarzyna.filmsapp.presentation.theme.FilmsAppTheme
import io.mockk.every
import io.mockk.mockk
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [34], qualifiers = "w411dp-h891dp-xxhdpi")
class FilterScreenScreenshotTest {

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

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun filterScreen_InitialState() {
        genresFlow.value = persistentListOf(
            GenreDataModel(1, "Action"),
            GenreDataModel(2, "Drama"),
            GenreDataModel(3, "Sci-Fi")
        )
        moviesFlow.value = persistentListOf()

        composeTestRule.setContent {
            FilmsAppTheme {
                FilterScreen(onMovieClick = {})
            }
        }

        composeTestRule.onRoot().captureRoboImage("screenshots/filter_screen_initial.png")
    }

    @Test
    fun filterScreen_WithFilteredResults() {
        genresFlow.value = persistentListOf(GenreDataModel(3, "Sci-Fi"))
        selectedGenreIdFlow.value = 3
        rangeValuesFlow.value = listOf(7.5f, 9.5f)

        moviesFlow.value = persistentListOf(
            MovieDataModel(
                movieID = "101",
                movieTitle = "Interstellar",
                movieDescription = "Space travel and time dilation.",
                movieRate = 8.7,
                moviePhoto = "",
                movieLiked = true
            ),
            MovieDataModel(
                movieID = "102",
                movieTitle = "The Martian",
                movieDescription = "Survival on Mars.",
                movieRate = 8.0,
                moviePhoto = "",
                movieLiked = false
            )
        )

        composeTestRule.setContent {
            FilmsAppTheme {
                FilterScreen(onMovieClick = {})
            }
        }

        composeTestRule.onRoot().captureRoboImage("screenshots/filter_screen_results.png")
    }
}
