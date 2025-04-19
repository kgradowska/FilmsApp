package gradowska.katarzyna.filmsapp.domain.usecase

import gradowska.katarzyna.filmsapp.data.UserDataSource
import io.mockk.MockKAnnotations
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class SetFavouriteMovieUseCaseTest {

    private lateinit var userDataSource: UserDataSource
    private lateinit var useCase: SetFavouriteMovieUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        userDataSource = mockk(relaxed = true)
        useCase = SetFavouriteMovieUseCase(userDataSource)
    }

    @Test
    fun `should call userDataSource to set movie as favourite`() {
        // arrange
        val movieId = "123"
        val isFavourite = true

        // act
        useCase.setMovieIsFavourite(movieId, isFavourite)

        // assert
        verify(exactly = 1) { userDataSource.setMovieIsFavourite(movieId, isFavourite) }
    }

    @Test
    fun `should call userDataSource to unset movie as favourite`() {
        // arrange
        val movieId = "456"
        val isFavourite = false

        // act
        useCase.setMovieIsFavourite(movieId, isFavourite)

        // assert
        verify(exactly = 1) { userDataSource.setMovieIsFavourite(movieId, isFavourite) }
    }
}
