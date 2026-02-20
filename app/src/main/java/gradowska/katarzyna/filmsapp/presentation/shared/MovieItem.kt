package gradowska.katarzyna.filmsapp.presentation.shared

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import gradowska.katarzyna.filmsapp.R
import gradowska.katarzyna.filmsapp.domain.entity.MovieDataModel
import gradowska.katarzyna.filmsapp.presentation.theme.Gold
import gradowska.katarzyna.filmsapp.presentation.theme.White
import gradowska.katarzyna.filmsapp.presentation.theme.WineBerry2
import gradowska.katarzyna.filmsapp.presentation.utils.formatRate

@Composable
fun MovieItem(
    movie: MovieDataModel,
    onItemClick: (MovieDataModel) -> Unit,
    onFavouriteClick: (MovieDataModel) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(5.dp)
            .clickable { onItemClick(movie) },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = WineBerry2
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 12.dp,
                    top = 20.dp,
                    end = 20.dp,
                    bottom = 20.dp
                )
        ) {
            AsyncImage(
                model = movie.moviePhoto,
                contentDescription = null,
                modifier = Modifier
                    .width(80.dp)
                    .height(120.dp)
                    .align(Alignment.CenterVertically),
                placeholder = painterResource(R.drawable.ic_poster_placeholder),
                error = painterResource(R.drawable.ic_poster_placeholder)
            )

            Spacer(modifier = Modifier.width(10.dp))

            MovieInfo(
                title = movie.movieTitle,
                description = movie.movieDescription,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(10.dp))

            Column(
                modifier = Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (movie.movieRate != null) {
                    val formattedRate = formatRate(movie.movieRate.toString())

                    Text(
                        text = formattedRate,
                        color = Gold,
                        fontSize = 32.sp,
                        modifier = Modifier.testTag("movie_rate_text")
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    painter = painterResource(
                        if (movie.movieLiked)
                            R.drawable.ic_baseline_star_rate_24
                        else
                            R.drawable.ic_baseline_star_border_24
                    ),
                    tint = Color.Unspecified,
                    contentDescription = null,
                    modifier = Modifier
                        .size(50.dp)
                        .clickable { onFavouriteClick(movie) }
                        .testTag("fav_icon")
                )

                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun MovieInfo(title: String, description: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxHeight(),
    ) {
        Text(
            text = title,
            maxLines = 2,
            color = Gold,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(Modifier.weight(1f))

        Text(
            text = description,
            maxLines = 5,
            color = White,
            fontSize = 15.sp,
            fontStyle = FontStyle.Italic,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MovieItemPreview() {
    MovieItem(
        movie = MovieDataModel(
            movieID = "abc",
            moviePhoto = "",
            movieTitle = "Movie title",
            movieDescription = "Short movie description which can take a few lines.",
            movieRate = 8.7,
            movieLiked = true
        ),
        onItemClick = {},
        onFavouriteClick = {}
    )
}
