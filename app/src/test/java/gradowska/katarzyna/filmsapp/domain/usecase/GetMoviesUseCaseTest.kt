package gradowska.katarzyna.filmsapp.domain.usecase

import gradowska.katarzyna.filmsapp.data.MovieDTO
import gradowska.katarzyna.filmsapp.data.MovieDataSource
import gradowska.katarzyna.filmsapp.data.entity.MoviesListDTO
import gradowska.katarzyna.filmsapp.domain.entity.MovieDataModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetMoviesUseCaseTest {

    @MockK
    private lateinit var movieDataSource: MovieDataSource

    @MockK
    private lateinit var getFavouriteMovieUseCase: GetFavouriteMovieUseCase

    private lateinit var useCase: GetMoviesUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = GetMoviesUseCase(movieDataSource, getFavouriteMovieUseCase)
    }

    @Test
    fun `should return movies list from API with favourite flags`() = runBlocking {
        // arrange
        val movieDto = MoviesListDTO.Result(
            id = 1,
            title = "API Movie",
            overview = "From API",
            posterPath = "/api.jpg",
            backdropPath = "/api_backdrop.jpg",
            voteAverage = 8.0,
            genreIds = listOf(1, 2),
            adult = false,
            originalTitle = "Original API Movie",
            originalLanguage = "en",
            voteCount = 200
        )

        coEvery { movieDataSource.getMoviesListFromApi(1) } returns MoviesListDTO(
            page = 1,
            totalPages = 1,
            results = listOf(movieDto),
            totalResults = 1
        )

        coEvery { getFavouriteMovieUseCase.getMovieIsFavourite("1") } returns true

        val expected = listOf(
            movieDto.toMovieDataModel(isFavourite = true)
        )

        // act
        val result = useCase.getMoviesList(loadFromApi = true, page = 1)

        // assert
        assertEquals(expected, result)
        coVerify(exactly = 1) {
            movieDataSource.getMoviesListFromApi(1)
            getFavouriteMovieUseCase.getMovieIsFavourite("1")
        }
    }

    @Test
    fun `should return movies list from local JSON with favourite flags`() = runBlocking {
        // arrange
        val movieDTO = MovieDTO(
            id = "2",
            title = "Local Movie",
            description = "From local JSON",
            imageRes = "/local.jpg",
            director = "John Doe",
            score = 7.0,
            releaseYear = "2020"
        )

        coEvery { movieDataSource.getMoviesListFromJson() } returns listOf(movieDTO)
        coEvery { getFavouriteMovieUseCase.getMovieIsFavourite("2") } returns true

        val expected = listOf(
            movieDTO.toMovieDataModel(isFavourite = true)
        )

        // act
        val result = useCase.getMoviesList(loadFromApi = false, page = 1)

        // assert
        assertEquals(expected, result)
        coVerify(exactly = 1) {
            movieDataSource.getMoviesListFromJson()
            getFavouriteMovieUseCase.getMovieIsFavourite("2")
        }
    }

    @Test
    fun `should return empty list when API returns no movies`() = runBlocking {
        // arrange
        coEvery { movieDataSource.getMoviesListFromApi(1) } returns MoviesListDTO(
            page = 1,
            totalPages = 1,
            results = emptyList(),
            totalResults = 0
        )

        // act
        val result = useCase.getMoviesList(loadFromApi = true, page = 1)

        // assert
        assertEquals(emptyList<MovieDataModel>(), result)
        coVerify(exactly = 1) {
            movieDataSource.getMoviesListFromApi(1)
        }
    }

    @Test
    fun `should return empty list when JSON returns no movies`() = runBlocking {
        // arrange
        coEvery { movieDataSource.getMoviesListFromJson() } returns emptyList()

        // act
        val result = useCase.getMoviesList(loadFromApi = false, page = 1)

        // assert
        assertEquals(emptyList<MovieDataModel>(), result)
        coVerify(exactly = 1) {
            movieDataSource.getMoviesListFromJson()
        }
    }
}
