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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import gradowska.katarzyna.filmsapp.R
import java.util.Locale

@Composable
fun MovieItem(
    movieImage: String,
    titleText: String,
    bodyText: String,
    rate: Double?,
    isLiked: Boolean,
    onItemClick: () -> Unit,
    onFavouriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(5.dp)
            .clickable { onItemClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.wineBerry2)
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
                model = movieImage,
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
                    text = titleText,
                    maxLines = 2,
                    color = colorResource(R.color.gold),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(Modifier.weight(1f))

                Text(
                    text = bodyText,
                    maxLines = 5,
                    color = colorResource(R.color.white),
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
                if (rate != null) {
                    Text(
                        text = String.format(Locale.getDefault(), "%.2f", rate),
                        color = colorResource(R.color.gold),
                        fontSize = 32.sp
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    painter = painterResource(
                        if (isLiked)
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

                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieItemPreview() {
    MovieItem(
        movieImage = "",
        titleText = "Movie title",
        bodyText = "Short movie description which can take a few lines.",
        rate = 8.7,
        isLiked = true,
        onItemClick = {},
        onFavouriteClick = {}
    )
}
