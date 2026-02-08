package gradowska.katarzyna.filmsapp.presentation.recyclerList.compose

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
import java.util.Locale

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
                    .align(Alignment.CenterVertically)
            )

            Spacer(modifier = Modifier.width(10.dp))

            Column(
                modifier = modifier
                    .weight(1f)
                    .fillMaxHeight(),
            ) {
                Text(
                    text = movie.movieTitle,
                    maxLines = 2,
                    color = Gold,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(Modifier.weight(1f))

                Text(
                    text = movie.movieDescription,
                    maxLines = 5,
                    color = White,
                    fontSize = 15.sp,
                    fontStyle = FontStyle.Italic,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(
                modifier = modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (movie.movieRate != null) {
                    Text(
                        text = String.format(Locale.getDefault(), "%.2f", movie.movieRate),
                        color = Gold,
                        fontSize = 32.sp
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
                )

                Spacer(modifier = Modifier.weight(1f))
            }
        }
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
