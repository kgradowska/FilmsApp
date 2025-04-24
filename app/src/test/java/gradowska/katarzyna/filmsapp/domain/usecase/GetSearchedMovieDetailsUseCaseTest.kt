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

class GetSearchedMovieDetailsUseCaseTest {

    @MockK
    private lateinit var movieDataSource: MovieDataSource

    @MockK
    private lateinit var getFavouriteMovieUseCase: GetFavouriteMovieUseCase

    private lateinit var useCase: GetSearchedMovieDetailsUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        useCase = GetSearchedMovieDetailsUseCase(movieDataSource, getFavouriteMovieUseCase)
    }

    @Test
    fun `should return list of MovieDataModel from search with favourite flags`() = runBlocking {
        // arrange
        val query = "Inception"
        val page = 1

        val movie = MoviesListDTO.Result(
            id = 10,
            title = "Inception",
            overview = "Dream within a dream",
            posterPath = "/inception.jpg",
            backdropPath = "/inception_bg.jpg",
            voteAverage = 8.8,
            genreIds = listOf(28, 878),
            adult = false,
            originalTitle = "Inception",
            originalLanguage = "en",
            voteCount = 25000
        )

        coEvery { movieDataSource.getSearchedMovieFromApi(query, page) } returns MoviesListDTO(
            page = 1,
            totalPages = 1,
            results = listOf(movie),
            totalResults = 1
        )

        coEvery { getFavouriteMovieUseCase.getMovieIsFavourite(movie.id.toString()) } returns true

        val expected = listOf(movie.toMovieDataModel(isFavourite = true))

        // act
        val result = useCase.getSearchedMovieList(query, page)

        // assert
        assertEquals(expected, result)
        coVerify(exactly = 1) {
            movieDataSource.getSearchedMovieFromApi(query, page)
            getFavouriteMovieUseCase.getMovieIsFavourite(movie.id.toString())
        }
    }

    @Test
    fun `should return empty list when search returns no results`() = runBlocking {
        // arrange
        val query = "UnknownMovie"
        val page = 1

        coEvery { movieDataSource.getSearchedMovieFromApi(query, page) } returns MoviesListDTO(
            page = 1,
            totalPages = 1,
            results = emptyList(),
            totalResults = 0
        )

        // act
        val result = useCase.getSearchedMovieList(query, page)

        // assert
        assertEquals(emptyList<MovieDataModel>(), result)
        coVerify(exactly = 1) {
            movieDataSource.getSearchedMovieFromApi(query, page)
        }
    }
}
