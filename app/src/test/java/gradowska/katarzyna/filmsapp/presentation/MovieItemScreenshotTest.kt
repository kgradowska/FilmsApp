package gradowska.katarzyna.filmsapp.presentation

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onRoot
import com.github.takahirom.roborazzi.captureRoboImage
import gradowska.katarzyna.filmsapp.domain.entity.MovieDataModel
import gradowska.katarzyna.filmsapp.presentation.shared.MovieItem
import gradowska.katarzyna.filmsapp.presentation.theme.FilmsAppTheme
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import org.koin.core.context.stopKoin

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [34], qualifiers = "w411dp-h891dp-xxhdpi")
class MovieItemScreenshotTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @After
    fun tearDown() {
        stopKoin()
    }

    private val mockMovie = MovieDataModel(
        movieID = "1",
        moviePhoto = "",
        movieTitle = "Inception",
        movieDescription = "A thief who steals corporate secrets through the use of dream-sharing technology.",
        movieRate = 8.55,
        movieLiked = false
    )

    @Test
    fun movieItem_LightMode_Unliked() {
        composeTestRule.setContent {
            FilmsAppTheme() {
                MovieItem(
                    movie = mockMovie,
                    onItemClick = {},
                    onFavouriteClick = {}
                )
            }
        }
        composeTestRule.onRoot().captureRoboImage("screenshots/movie_item_unliked.png")
    }

    @Test
    fun movieItem_DarkMode_Liked() {
        composeTestRule.setContent {
            FilmsAppTheme() {
                MovieItem(
                    movie = mockMovie.copy(movieLiked = true, movieTitle = "Interstellar"),
                    onItemClick = {},
                    onFavouriteClick = {}
                )
            }
        }
        composeTestRule.onRoot().captureRoboImage("screenshots/movie_item_liked.png")
    }

    @Test
    fun movieItem_LongText_ConstraintsCheck() {
        composeTestRule.setContent {
            FilmsAppTheme {
                MovieItem(
                    movie = mockMovie.copy(
                        movieTitle = "Very Long Movie Title That Might Break The Layout If Not Handled Properly",
                        movieDescription = "This is a very long description to see how the Ellipsis and MaxLines behave in our card component. It should cut off gracefully."
                    ),
                    onItemClick = {},
                    onFavouriteClick = {}
                )
            }
        }
        composeTestRule.onRoot().captureRoboImage("screenshots/movie_item_long_text.png")
    }
}
