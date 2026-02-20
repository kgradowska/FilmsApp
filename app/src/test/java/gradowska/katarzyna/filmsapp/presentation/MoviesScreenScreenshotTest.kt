package gradowska.katarzyna.filmsapp.presentation

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onRoot
import com.github.takahirom.roborazzi.captureRoboImage
import gradowska.katarzyna.filmsapp.domain.entity.MovieDataModel
import gradowska.katarzyna.filmsapp.presentation.searchMovies.SearchMoviesViewModel
import gradowska.katarzyna.filmsapp.presentation.searchMovies.MoviesScreen
import gradowska.katarzyna.filmsapp.presentation.theme.FilmsAppTheme
import io.mockk.every
import io.mockk.mockk
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableSharedFlow
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
class MoviesScreenScreenshotTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val viewModel: SearchMoviesViewModel = mockk(relaxed = true)

    private val moviesFlow = MutableStateFlow<PersistentList<MovieDataModel>>(persistentListOf())
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
    fun moviesScreen_EmptyState() {
        moviesFlow.value = persistentListOf()

        composeTestRule.setContent {
            FilmsAppTheme {
                MoviesScreen(onMovieClick = {})
            }
        }

        composeTestRule.onRoot().captureRoboImage("screenshots/movies_screen_empty.png")
    }

    @Test
    fun moviesScreen_WithData() {
        moviesFlow.value = persistentListOf(
            MovieDataModel(
                movieID = "1",
                movieTitle = "Inception",
                movieDescription = "A thief who steals corporate secrets through the use of dream-sharing technology.",
                movieRate = 8.8,
                moviePhoto = "",
                movieLiked = true
            ),
            MovieDataModel(
                movieID = "2",
                movieTitle = "Interstellar",
                movieDescription = "A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival.",
                movieRate = 8.7,
                moviePhoto = "",
                movieLiked = false
            ),
            MovieDataModel(
                movieID = "3",
                movieTitle = "The Dark Knight",
                movieDescription = "When the menace known as the Joker wreaks havoc and chaos on the people of Gotham.",
                movieRate = 9.0,
                moviePhoto = "",
                movieLiked = true
            )
        )

        composeTestRule.setContent {
            FilmsAppTheme {
                MoviesScreen(onMovieClick = {})
            }
        }

        composeTestRule.onRoot().captureRoboImage("screenshots/movies_screen_list.png")
    }
}
