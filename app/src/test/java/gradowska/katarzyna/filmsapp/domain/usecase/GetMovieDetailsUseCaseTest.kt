package gradowska.katarzyna.filmsapp.domain.usecase

import gradowska.katarzyna.filmsapp.data.MovieDataSource
import gradowska.katarzyna.filmsapp.data.entity.Genre
import gradowska.katarzyna.filmsapp.data.entity.MovieDetailsDTO
import gradowska.katarzyna.filmsapp.data.entity.ProductionCountry
import gradowska.katarzyna.filmsapp.data.entity.SpokenLanguage
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetMovieDetailsUseCaseTest {

    @MockK
    private lateinit var movieDataSource: MovieDataSource

    @MockK
    private lateinit var getFavouriteMoviesUseCase: GetFavouriteMoviesUseCase

    private lateinit var useCase: GetMovieDetailsUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        useCase = GetMovieDetailsUseCase(movieDataSource, getFavouriteMoviesUseCase)
    }

    private val movieDto = MovieDetailsDTO(
        id = 123,
        title = "Inception",
        overview = "A mind-bending thriller",
        backdropPath = "sthsth",
        genres = listOf(Genre(id = 1, name = "Comedy")),
        budget = 500000,
        originalTitle = "xyz",
        originalLanguage = "en",
        posterPath = "sth",
        productionCountries = listOf(ProductionCountry("xyz", "en")),
        releaseDate = "03.04.2024",
        revenue = 123123,
        runtime = 145,
        spokenLanguages = listOf(SpokenLanguage("qwerty", "en", "English")),
        tagline = "tagLine",
        video = false,
        voteCount = 500,
        voteAverage = 8.6
    )

    @Test
    fun `should return movie details when movie is favourite`() = runTest {
        // arrange
        val movieId = movieDto.id.toString()
        val favouritesList = listOf(movieId)

        coEvery { movieDataSource.getMovieFromApi(movieId) } returns movieDto
        every { getFavouriteMoviesUseCase() } returns flowOf(favouritesList)

        // act
        val result = useCase.getMovie(movieId).first()

        // assert
        assertEquals(true, result?.movieLiked)
        assertEquals("Inception", result?.movieTitle)
        coVerify(exactly = 1) { movieDataSource.getMovieFromApi(movieId) }
        verify(exactly = 1) { getFavouriteMoviesUseCase() }
    }

    @Test
    fun `should return movie details when movie is NOT favourite`() =
        runTest {
            // arrange
            val movieId = movieDto.id.toString()
            val favouritesList = listOf("999")

            coEvery { movieDataSource.getMovieFromApi(movieId) } returns movieDto
            every { getFavouriteMoviesUseCase() } returns flowOf(favouritesList)

            // act
            val result = useCase.getMovie(movieId).first()

            // assert
            assertEquals(false, result?.movieLiked)
            coVerify(exactly = 1) { movieDataSource.getMovieFromApi(movieId) }
            verify(exactly = 1) { getFavouriteMoviesUseCase() }
        }

    @Test
    fun `should return null when API returns no movie`() = runTest {
        // arrange
        val movieId = "789"
        coEvery { movieDataSource.getMovieFromApi(movieId) } returns null

        // act
        val result = useCase.getMovie(movieId).first()

        // assert
        assertEquals(null, result)
        coVerify(exactly = 1) { movieDataSource.getMovieFromApi(movieId) }
        verify(exactly = 0) { getFavouriteMoviesUseCase() }
    }
}
