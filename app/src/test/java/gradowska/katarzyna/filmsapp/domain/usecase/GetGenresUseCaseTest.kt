package gradowska.katarzyna.filmsapp.domain.usecase

import gradowska.katarzyna.filmsapp.data.MovieDataSource
import gradowska.katarzyna.filmsapp.data.entity.GenresDTO
import gradowska.katarzyna.filmsapp.domain.entity.GenreDataModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetGenresUseCaseTest {

    @MockK
    private lateinit var movieDataSource: MovieDataSource

    private lateinit var useCase: GetGenresUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        useCase = GetGenresUseCase(movieDataSource)
    }

    @Test
    fun `should return list of genres when genres are fetched from API`() = runBlocking {
        // arrange
        val genre1 = GenresDTO.Genre(id = 1, name = "Action")
        val genre2 = GenresDTO.Genre(id = 2, name = "Comedy")
        val expected = listOf(genre1.toGenreDataModel(), genre2.toGenreDataModel())

        coEvery { movieDataSource.getGenresFromApi() } returns GenresDTO(
            genres = listOf(
                genre1,
                genre2
            )
        )

        // act
        val result = useCase.getGenres()

        // assert
        assertEquals(expected, result)
        coVerify(exactly = 1) { movieDataSource.getGenresFromApi() }
    }

    @Test
    fun `should return empty list when API returns no genres`() = runBlocking {
        // arrange
        coEvery { movieDataSource.getGenresFromApi() } returns GenresDTO(genres = emptyList())

        // act
        val result = useCase.getGenres()

        // assert
        assertEquals(emptyList<GenreDataModel>(), result)
        coVerify(exactly = 1) { movieDataSource.getGenresFromApi() }
    }

}