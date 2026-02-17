package gradowska.katarzyna.filmsapp.domain.usecase

import gradowska.katarzyna.filmsapp.data.MovieDataSource
import gradowska.katarzyna.filmsapp.data.entity.MoviesListDTO
import gradowska.katarzyna.filmsapp.domain.entity.MovieDataModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
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
    fun `should return list of MovieDataModel from search with favourite flags`() = runTest {
        // arrange
        val query = "Inception"
        val page = 1
        val movieId = "10"

        val movieDto = MoviesListDTO.Result(
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
            results = listOf(movieDto),
            totalResults = 1
        )

        every { getFavouriteMovieUseCase(movieId) } returns flowOf(true)

        val expected = listOf(movieDto.toMovieDataModel(isFavourite = true))

        // act
        val result = useCase.getSearchedMovieList(query, page)

        // assert
        assertEquals(expected, result)
        coVerify(exactly = 1) { movieDataSource.getSearchedMovieFromApi(query, page) }
        verify(exactly = 1) { getFavouriteMovieUseCase(movieId) }
    }

    @Test
    fun `should return empty list when search returns no results`() = runTest {
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
        assertEquals(0, result.size)
        assertEquals(emptyList<MovieDataModel>(), result)
        coVerify(exactly = 1) { movieDataSource.getSearchedMovieFromApi(query, page) }
        verify(exactly = 0) { getFavouriteMovieUseCase(any()) }
    }
}
