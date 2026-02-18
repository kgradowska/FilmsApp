package gradowska.katarzyna.filmsapp.presentation

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onRoot
import com.github.takahirom.roborazzi.captureRoboImage
import gradowska.katarzyna.filmsapp.presentation.singleMovie.SingleMovie
import gradowska.katarzyna.filmsapp.presentation.theme.FilmsAppTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [34], qualifiers = "w411dp-h891dp-xxhdpi")
class SingleMovieScreenshotTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun captureSingleMovieScreen() {
        composeTestRule.setContent {
            FilmsAppTheme() {
                SingleMovie(
                    titleText = "Interstellar",
                    movieImage = "",
                    movieBackdropPath = "",
                    description = "A team of explorers travel through a wormhole in space...",
                    rate = "8.7",
                    isLiked = true,
                    genres = "Sci-Fi, Drama",
                    runtime = "169 min",
                    dateOfProduction = "2014",
                    viewsCounter = "1500000",
                    quote = "Mankind was born on Earth. It was never meant to die here.",
                    productionCountries = "USA, UK",
                    originalLanguage = "English",
                    originalTitle = "Interstellar",
                    budget = "165 000 000 $",
                    revenue = "677 000 000 $",
                    onFavouriteClick = {}
                )
            }
        }

        composeTestRule.onRoot().captureRoboImage(filePath = "screenshots/movie_details_dark.png")
    }
}
