package gradowska.katarzyna.filmsapp.presentation

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import gradowska.katarzyna.filmsapp.domain.entity.MovieDataModel
import gradowska.katarzyna.filmsapp.presentation.recyclerList.compose.MovieItem
import org.junit.Rule
import org.junit.Test

class MovieItemTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val mockMovie = MovieDataModel(
        movieID = "123",
        movieTitle = "Interstellar",
        movieDescription = "Space travel odyssey",
        movieRate = 8.85,
        moviePhoto = "",
        movieLiked = false
    )

    @Test
    fun movieItem_ShouldDisplayCorrectData() {
        // arrange
        composeTestRule.setContent {
            MovieItem(
                movie = mockMovie,
                onItemClick = {},
                onFavouriteClick = {}
            )
        }

        // assert
        composeTestRule.onNodeWithText("Interstellar").assertIsDisplayed()
        composeTestRule.onNodeWithText("Space travel odyssey").assertIsDisplayed()
        composeTestRule.onNodeWithText("8.85").assertIsDisplayed()
    }

    @Test
    fun movieItem_ShouldClickEntireCard() {
        // arrange
        var clickedMovie: MovieDataModel? = null
        composeTestRule.setContent {
            MovieItem(
                movie = mockMovie,
                onItemClick = { clickedMovie = it },
                onFavouriteClick = {}
            )
        }

        // act
        composeTestRule.onNodeWithText("Interstellar").performClick()

        // assert
        assert(clickedMovie?.movieID == "123")
    }

    @Test
    fun movieItem_ShouldClickOnlyFavoriteIcon() {
        // arrange
        var favoriteClicked = false
        var cardClicked = false

        composeTestRule.setContent {
            MovieItem(
                movie = mockMovie,
                onItemClick = { cardClicked = true },
                onFavouriteClick = { favoriteClicked = true }
            )
        }

        // act
        composeTestRule.onNodeWithTag("fav_icon").performClick()

        // assert
        assert(favoriteClicked)
        assert(!cardClicked)
    }

    @Test
    fun movieItem_ShouldNotDisplayRate_WhenRateIsNull() {
        // arrange
        val movieWithoutRate = mockMovie.copy(movieRate = null)

        composeTestRule.setContent {
            MovieItem(
                movie = movieWithoutRate,
                onItemClick = {},
                onFavouriteClick = {}
            )
        }

        // assert
        composeTestRule.onNodeWithTag("movie_rate_text").assertDoesNotExist()
    }
}