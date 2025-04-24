package gradowska.katarzyna.filmsapp.domain.usecase

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

class GetMoviesGenresUseCaseTest {

    @MockK
    private lateinit var movieDataSource: MovieDataSource

    @MockK
    private lateinit var getFavouriteMovieUseCase: GetFavouriteMovieUseCase

    private lateinit var useCase: GetMoviesGenresUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        useCase = GetMoviesGenresUseCase(movieDataSource, getFavouriteMovieUseCase)
    }

    @Test
    fun `should return list of MovieDataModel with favourite flags`() = runBlocking {
        // arrange
        val result1 = MoviesListDTO.Result(
            id = 1,
            title = "Movie 1",
            overview = "Overview 1",
            posterPath = "/poster1.jpg",
            backdropPath = "/backdrop1.jpg",
            voteAverage = 8.5,
            genreIds = listOf(1, 28),
            adult = true,
            originalTitle = "Title1",
            originalLanguage = "en",
            voteCount = 560
        )
        val result2 = MoviesListDTO.Result(
            id = 2,
            title = "Movie 2",
            overview = "Overview 2",
            posterPath = "/poster2.jpg",
            backdropPath = "/backdrop2.jpg",
            voteAverage = 7.2,
            genreIds = listOf(2, 28),
            adult = false,
            originalTitle = "Title2",
            originalLanguage = "fr",
            voteCount = 590
        )

        coEvery {
            movieDataSource.getMoviesInGenre(
                any(),
                any(),
                any(),
                any(),
                any(),
                any()
            )
        } returns MoviesListDTO(
            page = 1,
            totalPages = 1,
            results = listOf(result1, result2),
            totalResults = 2
        )
        coEvery { getFavouriteMovieUseCase.getMovieIsFavourite("1") } returns true
        coEvery { getFavouriteMovieUseCase.getMovieIsFavourite("2") } returns false

        val expected = listOf(
            result1.toMovieDataModel(isFavourite = true),
            result2.toMovieDataModel(isFavourite = false)
        )

        // act
        val result = useCase.getMovieList(
            query = null,
            currentPage = 1,
            sortBy = "popularity.desc",
            withGenres = "28",
            voteAverageGte = 5.0f,
            voteAverageLte = 9.0f
        )

        // assert
        assertEquals(expected, result)
        coVerify(exactly = 1) {
            movieDataSource.getMoviesInGenre(any(), any(), any(), any(), any(), any())
            getFavouriteMovieUseCase.getMovieIsFavourite("1")
            getFavouriteMovieUseCase.getMovieIsFavourite("2")
        }
        assertEquals(result[0].movieID, result1.id.toString())
        assertEquals(result[0].movieLiked, true)
        assertEquals(result[0].movieRate, result1.voteAverage)
        assertEquals(
            result[0].moviePhoto,
            "https://image.tmdb.org/t/p/original/" + result1.posterPath
        )
        assertEquals(result[0].movieTitle, result1.title)
        assertEquals(result[0].movieDescription, result1.overview)
    }

    @Test
    fun `should return empty list when no movies are returned`() = runBlocking {
        // arrange
        coEvery {
            movieDataSource.getMoviesInGenre(
                any(),
                any(),
                any(),
                any(),
                any(),
                any()
            )
        } returns MoviesListDTO(
            page = 1,
            totalPages = 1,
            results = emptyList(),
            totalResults = 0
        )

        // act
        val result = useCase.getMovieList(
            query = null,
            currentPage = 1,
            sortBy = null,
            withGenres = "35",
            voteAverageGte = null,
            voteAverageLte = null
        )

        // assert
        assertEquals(emptyList<MovieDataModel>(), result)
        coVerify(exactly = 1) {
            movieDataSource.getMoviesInGenre(
                any(),
                any(),
                any(),
                any(),
                any(),
                any()
            )
        }
    }
}
