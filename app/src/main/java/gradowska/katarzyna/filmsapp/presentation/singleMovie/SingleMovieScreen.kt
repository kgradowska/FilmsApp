package gradowska.katarzyna.filmsapp.presentation.singleMovie

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import gradowska.katarzyna.filmsapp.R
import gradowska.katarzyna.filmsapp.domain.entity.MovieDetailsDataModel
import gradowska.katarzyna.filmsapp.presentation.shared.LoadingScreen
import gradowska.katarzyna.filmsapp.presentation.theme.Gold
import gradowska.katarzyna.filmsapp.presentation.theme.Tolopea
import gradowska.katarzyna.filmsapp.presentation.theme.White
import gradowska.katarzyna.filmsapp.presentation.theme.WineBerry2
import gradowska.katarzyna.filmsapp.presentation.utils.formatRate
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun SingleMovieScreen(
    movieId: Int,
    viewModel: SingleMovieViewModel = koinViewModel { parametersOf(movieId.toString()) }
) {
    val movieDetails by viewModel.movieDetails.collectAsStateWithLifecycle()

    movieDetails?.let { movie ->
        SingleMovie(
            movie = movie,
            onFavouriteClick = { viewModel.favouriteIconClicked(movie) }
        )
    } ?: run {
        LoadingScreen()
    }
}

@Composable
fun SingleMovie(
    movie: MovieDetailsDataModel,
    onFavouriteClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Tolopea)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(
                    rememberScrollState()
                )
                .padding(bottom = 50.dp) // the place for the sticky row
        ) {
            MovieHeader(movie = movie, onFavouriteClick = onFavouriteClick)

            MovieContent(movie = movie)
        }

        BottomBar(
            budget = movie.budget,
            revenue = movie.revenue
        )

    }
}

@Composable
fun MovieHeader(
    movie: MovieDetailsDataModel,
    onFavouriteClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
    ) {

        AsyncImage(
            model = movie.backdropPath,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.ic_poster_placeholder),
            error = painterResource(R.drawable.ic_poster_placeholder)
        )

        Column(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 15.dp, end = 10.dp)
                .width(100.dp)
                .height(168.dp)
                .background(
                    color = WineBerry2.copy(alpha = 0.8f),
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            if (movie.rate.toDoubleOrNull() != null) {
                Text(
                    text = formatRate(movie.rate),
                    color = Gold,
                    fontSize = 32.sp,
                    textAlign = TextAlign.Center
                )
            }

            Icon(
                painter = painterResource(
                    if (movie.isLiked)
                        R.drawable.ic_baseline_star_rate_24
                    else
                        R.drawable.ic_baseline_star_border_24
                ),
                tint = Color.Unspecified,
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .clickable { onFavouriteClick() }
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = movie.voteCount,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = Gold
            )

            Text(
                text = pluralStringResource(
                    id = R.plurals.votes,
                    count = movie.voteCount.toIntOrNull() ?: 0
                ),
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = Gold
            )
        }
    }
}

@Composable
fun MovieContent(movie: MovieDetailsDataModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 20.dp)
    ) {
        Text(
            text = movie.title,
            fontSize = 33.sp,
            lineHeight = 40.sp,
            fontWeight = FontWeight.Bold,
            color = Gold
        )

        Spacer(Modifier.height(10.dp))

        Text(
            text = movie.genres,
            fontSize = 16.sp,
            color = Gold,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
        )

        Spacer(Modifier.height(6.dp))

        Text(
            text = movie.runtime + "  | " + movie.releaseDate,
            fontSize = 16.sp,
            color = Gold,
        )

        Spacer(Modifier.height(16.dp))

        if (movie.quote.isNotBlank()) {
            QuoteBox(movie.quote)
            Spacer(Modifier.height(10.dp))
        }

        Row(
            modifier = Modifier
                .height(180.dp)
                .padding(vertical = 8.dp)
        ) {

            AsyncImage(
                model = movie.photo,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .align(Alignment.CenterVertically),
                placeholder = painterResource(R.drawable.ic_poster_placeholder),
                error = painterResource(R.drawable.ic_poster_placeholder)
            )

            Spacer(modifier = Modifier.width(10.dp))

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(2f), Arrangement.SpaceBetween
            ) {
                LabeledText(
                    label = stringResource(R.string.production_countries),
                    value = movie.productionCountries
                )

                LabeledText(
                    label = stringResource(R.string.original_language),
                    value = movie.originalLanguage
                )

                LabeledText(
                    label = stringResource(R.string.original_title),
                    value = movie.originalTitle
                )
            }
        }

        Spacer(Modifier.height(10.dp))

        Text(
            text = movie.description,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            fontSize = 15.sp,
            color = White,
            modifier = Modifier.padding(bottom = 30.dp)
        )

        Spacer(Modifier.height(10.dp))

    }
}

@Composable
fun QuoteBox(quote: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(
                color = WineBerry2,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(vertical = 10.dp, horizontal = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = quote,
            fontSize = 25.sp,
            textAlign = TextAlign.Center,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold,
            color = White
        )
    }
}

@Composable
fun BoxScope.BottomBar(
    budget: String,
    revenue: String
) {
    val showBudget = budget != "0 $" && budget.isNotBlank()
    val showRevenue = revenue != "0 $" && revenue.isNotBlank()

    if (!showBudget && !showRevenue) return

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .clip(
                RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp
                )
            )
            .background(WineBerry2)
            .align(Alignment.BottomCenter),
    ) {
        if (showBudget) {
            BudgetColumn(stringResource(R.string.budget), budget)
        }
        if (showRevenue) {
            BudgetColumn(stringResource(R.string.revenue), revenue)
        }
    }
}

@Composable
private fun RowScope.BudgetColumn(title: String, value: String) {
    Column(
        modifier = Modifier
            .weight(1f)
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = 6.dp,
            alignment = Alignment.CenterVertically
        )
    ) {
        Text(title, fontWeight = FontWeight.Bold, color = Gold)
        Text(value, fontStyle = FontStyle.Italic, color = Gold)
    }
}

@Composable
fun LabeledText(
    label: String,
    value: String
) {
    if (value.isNotBlank()) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Gold
        )
        Text(
            text = value,
            fontStyle = FontStyle.Italic,
            fontSize = 16.sp,
            color = Gold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SingleMoviePreview() {
    val mockMovie = MovieDetailsDataModel(
        id = "1",
        title = "The Shawshank Redemption",
        photo = "https://image.tmdb.org/t/p/w500/los_angeles.jpg",
        backdropPath = "",
        rate = "8.32",
        isLiked = false,
        voteCount = "235 325",
        productionCountries = "US",
        originalLanguage = "English",
        originalTitle = "Title",
        description = "Lorem ipsum Lorem ipsumLorem ipsumLorem ipsumLorem ipsum Lorem ipsum Lorem ipsumLorem ipsumLorem ipsumLorem ipsum Lorem ipsum Lorem ipsumLorem ipsumLorem ipsumLorem ipsum Lorem ipsum Lorem ipsumLorem ipsumLorem ipsumLorem ipsum Lorem ipsum Lorem ipsumLorem ipsumLorem ipsumLorem ipsum Lorem ipsum Lorem ipsumLorem ipsumLorem ipsumLorem ipsum Lorem ipsum Lorem ipsumLorem ipsumLorem ipsumLorem ipsum",
        quote = "Looooooooooooooooooooong quoooooooooooooooooooooote ",
        budget = "60000",
        revenue = "234234352",
        genres = "Comedy",
        runtime = "200 min",
        releaseDate = "2003-04-01"
    )

    SingleMovie(
        movie = mockMovie,
        onFavouriteClick = {}
    )
}
