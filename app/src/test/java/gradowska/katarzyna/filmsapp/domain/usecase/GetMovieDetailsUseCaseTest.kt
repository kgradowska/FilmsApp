package gradowska.katarzyna.filmsapp.domain.usecase

import gradowska.katarzyna.filmsapp.data.MovieDataSource
import gradowska.katarzyna.filmsapp.data.entity.Genre
import gradowska.katarzyna.filmsapp.data.entity.MovieDetailsDTO
import gradowska.katarzyna.filmsapp.data.entity.ProductionCountry
import gradowska.katarzyna.filmsapp.data.entity.SpokenLanguage
import gradowska.katarzyna.filmsapp.domain.entity.MovieDetailsDataModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetMovieDetailsUseCaseTest {

    @MockK
    private lateinit var movieDataSource: MovieDataSource

    @MockK
    private lateinit var getFavouriteMovieUseCase: GetFavouriteMovieUseCase

    private lateinit var useCase: GetMovieDetailsUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = GetMovieDetailsUseCase(movieDataSource, getFavouriteMovieUseCase)
    }

    private val movie = MovieDetailsDTO(
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
    fun `should return movie details when movie is fetched from API`() = runBlocking {
        // arrange
        val isFavourite = true
        val expectedMovieDetails = MovieDetailsDataModel(
            movieID = "123",
            movieTitle = "Inception",
            movieDescription = "A mind-bending thriller",
            moviePhoto = "https://image.tmdb.org/t/p/original/" + "sth",
            movieLiked = isFavourite,
            movieRate = "8.6",
            movieReleaseDate = "   " + "03.04.2024",
            movieVoteCount = "500",
            movieGenres = "Comedy",
            movieRuntime = "145 min",
            movieBudget = "500000 $",
            movieRevenue = "123123 $",
            movieOriginalLanguage = "en",
            movieOriginalTitle = "xyz",
            movieTagline = "  " + "tagLine" + "  ",
            movieBackdropPath = "https://image.tmdb.org/t/p/original/" + "sthsth",
            movieProductionCountries = "en"
        )

        coEvery { movieDataSource.getMovieFromApi(movie.id.toString()) } returns movie
        coEvery { getFavouriteMovieUseCase.getMovieIsFavourite(movie.id.toString()) } returns isFavourite

        // act
        val result = useCase.getMovie(movie.id.toString())

        // assert
        assertEquals(expectedMovieDetails, result)
        coVerify(exactly = 1) { movieDataSource.getMovieFromApi(movie.id.toString()) }
        coVerify(exactly = 1) { getFavouriteMovieUseCase.getMovieIsFavourite(movie.id.toString()) }
    }

    @Test
    fun `should return movie details with false favourite status when movie is not favourite`() =
        runBlocking {
            // arrange
            val isFavourite = false
            val expectedMovieDetails = MovieDetailsDataModel(
                movieID = "123",
                movieTitle = "Inception",
                movieDescription = "A mind-bending thriller",
                moviePhoto = "https://image.tmdb.org/t/p/original/" + "sth",
                movieLiked = isFavourite,
                movieRate = "8.6",
                movieReleaseDate = "   " + "03.04.2024",
                movieVoteCount = "500",
                movieGenres = "Comedy",
                movieRuntime = "145 min",
                movieBudget = "500000 $",
                movieRevenue = "123123 $",
                movieOriginalLanguage = "en",
                movieOriginalTitle = "xyz",
                movieTagline = "  " + "tagLine" + "  ",
                movieBackdropPath = "https://image.tmdb.org/t/p/original/" + "sthsth",
                movieProductionCountries = "en"
            )

            coEvery { movieDataSource.getMovieFromApi(movie.id.toString()) } returns movie
            coEvery { getFavouriteMovieUseCase.getMovieIsFavourite(movie.id.toString()) } returns isFavourite

            // act
            val result = useCase.getMovie(movie.id.toString())

            // assert
            assertEquals(expectedMovieDetails, result)
            coVerify(exactly = 1) { movieDataSource.getMovieFromApi(movie.id.toString()) }
            coVerify(exactly = 1) { getFavouriteMovieUseCase.getMovieIsFavourite(movie.id.toString()) }
        }


    @Test
    fun `should handle case where movie is not found in API`() = runBlocking {
        // arrange
        val movieId = "789"
        coEvery { movieDataSource.getMovieFromApi(movieId) } returns null

        // act
        val result = useCase.getMovie(movieId)

        // assert
        assertEquals(null, result)
        coVerify(exactly = 1) { movieDataSource.getMovieFromApi(movieId) }
        coVerify(exactly = 0) { getFavouriteMovieUseCase.getMovieIsFavourite(movieId) }
    }

}