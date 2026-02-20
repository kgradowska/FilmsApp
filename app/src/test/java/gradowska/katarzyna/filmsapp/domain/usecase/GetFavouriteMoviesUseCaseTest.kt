package gradowska.katarzyna.filmsapp.domain.usecase

import gradowska.katarzyna.filmsapp.data.UserDataSource
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetFavouriteMoviesUseCaseTest {
    @MockK
    private lateinit var userDataSource: UserDataSource

    private lateinit var useCase: GetFavouriteMoviesUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        useCase = GetFavouriteMoviesUseCase(userDataSource)
    }

    @Test
    fun `should return list of favourite movie ids`() = runTest {
        // arrange
        val favouriteMovies = persistentListOf("123", "456", "789")
        every { userDataSource.getFavouriteMovies() } returns flowOf(favouriteMovies)

        // act
        val result = useCase().first()

        // assert
        assertEquals(favouriteMovies, result)
        assertEquals(3, result.size)
        verify(exactly = 1) { userDataSource.getFavouriteMovies() }
    }

    @Test
    fun `should return empty list when no movies are favourite`() = runTest {
        // arrange
        val emptyList = persistentListOf<String>()
        every { userDataSource.getFavouriteMovies() } returns flowOf(emptyList)

        // act
        val result = useCase().first()

        // assert
        assertEquals(0, result.size)
        verify(exactly = 1) { userDataSource.getFavouriteMovies() }
    }
}
