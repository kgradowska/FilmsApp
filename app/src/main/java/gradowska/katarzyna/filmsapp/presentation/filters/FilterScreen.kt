package gradowska.katarzyna.filmsapp.presentation.filters

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import gradowska.katarzyna.filmsapp.domain.entity.GenreDataModel
import gradowska.katarzyna.filmsapp.presentation.shared.MovieItem
import gradowska.katarzyna.filmsapp.presentation.theme.Gold
import gradowska.katarzyna.filmsapp.presentation.theme.Tolopea
import gradowska.katarzyna.filmsapp.presentation.theme.White
import gradowska.katarzyna.filmsapp.presentation.theme.WineBerry2
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterScreen(
    onMovieClick: (String) -> Unit,
    viewModel: MoviesGenresViewModel = koinViewModel()
) {
    val movies by viewModel.moviesList.collectAsStateWithLifecycle()
    val genres by viewModel.genresList.collectAsStateWithLifecycle()
    val selectedGenreId by viewModel.selectedGenreId.collectAsStateWithLifecycle()
    val rangeValues by viewModel.rangeValues.collectAsStateWithLifecycle()
    var expanded by remember { mutableStateOf(false) }
    val selectedOption = genres.find { it.id == selectedGenreId }?.name ?: "Choose genre"

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shadowElevation = 4.dp,
            color = WineBerry2
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DropdownGenreSelector(
                    expanded = expanded,
                    selectedOption = selectedOption,
                    options = genres,
                    onExpandedChange = { expanded = it },
                    onOptionSelected = { genreName ->
                        val selectedGenre = genres.find { it.name == genreName }
                        viewModel.onGenreSelected(selectedGenre?.id)
                        expanded = false
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Column {
                    Text(
                        text = "Rating: ${"%.1f".format(rangeValues[0])} - ${
                            "%.1f".format(
                                rangeValues[1]
                            )
                        }",
                        color = White
                    )
                    RangeSlider(
                        value = rangeValues[0]..rangeValues[1],
                        onValueChange = { range ->
                            viewModel.onSliderRangeChanged(listOf(range.start, range.endInclusive))
                        },
                        valueRange = 0f..10f,
                        colors = SliderDefaults.colors(
                            thumbColor = Gold,
                            activeTrackColor = Gold,
                            inactiveTrackColor = White.copy(alpha = 0.24f),
                            activeTickColor = WineBerry2,
                            inactiveTickColor = White
                        )
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = {
                        viewModel.searchButtonClicked()
                        coroutineScope.launch {
                            listState.animateScrollToItem(0)
                        }
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Gold,
                        contentColor = WineBerry2
                    )
                ) {
                    Text(
                        text = "SEARCH",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .background(Tolopea),
            contentPadding = PaddingValues(16.dp)
        ) {
            itemsIndexed(movies) { index, movie ->
                MovieItem(
                    movie = movie,
                    onItemClick = { clickedMovie -> onMovieClick(clickedMovie.movieID) },
                    onFavouriteClick = { viewModel.favouriteIconClicked(it) }
                )

                if (index == movies.lastIndex) {
                    LaunchedEffect(index) {
                        viewModel.listEndReached()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownGenreSelector(
    expanded: Boolean,
    selectedOption: String,
    options: List<GenreDataModel>,
    onExpandedChange: (Boolean) -> Unit,
    onOptionSelected: (String) -> Unit
) {
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { onExpandedChange(it) }
    ) {
        TextField(
            value = selectedOption,
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
                .testTag("genre_text_field"),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Tolopea,
                unfocusedTextColor = Tolopea,
                focusedTrailingIconColor = Tolopea,
                unfocusedTrailingIconColor = Tolopea,
                focusedContainerColor = Gold,
                unfocusedContainerColor = Gold,
                focusedIndicatorColor = Gold,
                unfocusedIndicatorColor = Gold.copy(alpha = 0.5f)
            )
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) },
            modifier = Modifier.background(Gold)
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option.name) },
                    onClick = {
                        onOptionSelected(option.name)
                    },
                    colors = MenuDefaults.itemColors(
                        textColor = Tolopea,
                        leadingIconColor = Tolopea,
                        trailingIconColor = Tolopea,
                    )
                )
            }
        }
    }
}
