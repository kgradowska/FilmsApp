package gradowska.katarzyna.filmsapp.presentation

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import gradowska.katarzyna.filmsapp.presentation.recyclerList.compose.SingleMovie
import org.junit.Rule
import org.junit.Test

class SingleMovieTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun movieDetails_ShouldDisplayAllRequiredInformation() {
        // arrange
        composeTestRule.setContent {
            SingleMovie(
                titleText = "Inception",
                description = "Dream within a dream",
                rate = "8.8",
                isLiked = false,
                viewsCounter = "100",
                genres = "Sci-Fi",
                runtime = "148 min",
                dateOfProduction = "2010",
                quote = "Your mind is the scene of the crime",
                budget = "160M",
                revenue = "828M",
                movieImage = "",
                movieBackdropPath = "",
                productionCountries = "USA",
                originalLanguage = "EN",
                originalTitle = "Original Inception",
                onFavouriteClick = {}
            )
        }

        // assert
        composeTestRule.onNodeWithText("Inception").assertIsDisplayed()
        composeTestRule.onNodeWithText("Sci-Fi").assertIsDisplayed()
        composeTestRule.onNodeWithText("Dream within a dream").assertIsDisplayed()
        composeTestRule.onNodeWithText("160M").assertIsDisplayed()
    }

    @Test
    fun favoriteIcon_ShouldTriggerCallback_WhenClicked() {
        //arrange
        var clicked = false

        composeTestRule.setContent {
            SingleMovie(
                titleText = "Test",
                isLiked = false,
                rate = "5.0",
                viewsCounter = "10",
                onFavouriteClick = { clicked = true },
                movieImage = "",
                movieBackdropPath = "",
                description = "Dream within a dream",
                genres = "Sci-Fi",
                runtime = "148 min",
                dateOfProduction = "2010",
                quote = "Your mind is the scene of the crime",
                productionCountries = "USA",
                originalLanguage = "EN",
                originalTitle = "Original Inception",
                budget = "160M",
                revenue = "828M",
            )
        }

        //act
        composeTestRule.onNode(hasClickAction()).performClick()

        // assert
        assert(clicked)
    }
}
