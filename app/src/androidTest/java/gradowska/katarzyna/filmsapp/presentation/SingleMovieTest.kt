package gradowska.katarzyna.filmsapp.presentation

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import gradowska.katarzyna.filmsapp.domain.entity.MovieDetailsDataModel
import gradowska.katarzyna.filmsapp.presentation.singleMovie.SingleMovie
import org.junit.Rule
import org.junit.Test

class SingleMovieTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun createMockMovie(
        title: String = "Inception",
        description: String = "Dream within a dream",
        budget: String = "160M",
        revenue: String = "828M"
    ) = MovieDetailsDataModel(
        id = "1",
        title = title,
        photo = "",
        backdropPath = "",
        rate = "8.8",
        isLiked = false,
        voteCount = "100",
        productionCountries = "USA",
        originalLanguage = "EN",
        originalTitle = "Original Inception",
        description = description,
        quote = "Your mind is the scene of the crime",
        budget = budget,
        revenue = revenue,
        genres = "Sci-Fi",
        runtime = "148 min",
        releaseDate = "2010"
    )

    @Test
    fun movieDetails_ShouldDisplayAllRequiredInformation() {
        // arrange
        val movie = createMockMovie()
        composeTestRule.setContent {
            SingleMovie(
                movie = movie,
                onFavouriteClick = {}
            )
        }

        // assert
        composeTestRule.onNodeWithText("Inception").assertIsDisplayed()
        composeTestRule.onNodeWithText("Sci-Fi").assertIsDisplayed()
        composeTestRule.onNodeWithText("Dream within a dream").assertIsDisplayed()
        composeTestRule.onNodeWithText("160M").assertIsDisplayed()
        composeTestRule.onNodeWithText("828M").assertIsDisplayed()
    }

    @Test
    fun favoriteIcon_ShouldTriggerCallback_WhenClicked() {
        //arrange
        var clicked = false
        val movie = createMockMovie()
        composeTestRule.setContent {
            SingleMovie(
                movie = movie,
                onFavouriteClick = { clicked = true }
            )
        }

        //act
        composeTestRule.onNode(hasClickAction()).performClick()

        // assert
        assert(clicked)
    }
}
