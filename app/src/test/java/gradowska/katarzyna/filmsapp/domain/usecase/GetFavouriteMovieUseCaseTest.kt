package gradowska.katarzyna.filmsapp.domain.usecase

import gradowska.katarzyna.filmsapp.data.UserDataSource
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import io.mockk.every
import io.mockk.verify

class GetFavouriteMovieUseCaseTest {

    @MockK
    private lateinit var userDataSource: UserDataSource

    private lateinit var useCase: GetFavouriteMovieUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        useCase = GetFavouriteMovieUseCase(userDataSource)
    }

    @Test
    fun `should return true when movie is favourite`() {
        // arrange
        val movieId = "123"
        every { userDataSource.getMovieIsFavourite(movieId) } returns true

        // act
        val result = useCase.getMovieIsFavourite(movieId)

        // assert
        assertEquals(true, result)
        verify(exactly = 1) { userDataSource.getMovieIsFavourite(movieId) }
    }

    @Test
    fun `should return false when movie is not favourite`() {
        // arrange
        val movieId = "456"
        every { userDataSource.getMovieIsFavourite(movieId) } returns false

        // act
        val result = useCase.getMovieIsFavourite(movieId)

        // assert
        assertEquals(false, result)
        verify(exactly = 1) { userDataSource.getMovieIsFavourite(movieId) }
    }
}
