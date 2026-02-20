package gradowska.katarzyna.filmsapp.presentation.filters

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyListState
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
import androidx.compose.runtime.derivedStateOf
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
import gradowska.katarzyna.filmsapp.domain.entity.MovieDataModel
import gradowska.katarzyna.filmsapp.presentation.shared.EmptyMoviesPlaceholder
import gradowska.katarzyna.filmsapp.presentation.shared.LoadingScreen
import gradowska.katarzyna.filmsapp.presentation.shared.MovieItem
import gradowska.katarzyna.filmsapp.presentation.theme.Gold
import gradowska.katarzyna.filmsapp.presentation.theme.Tolopea
import gradowska.katarzyna.filmsapp.presentation.theme.White
import gradowska.katarzyna.filmsapp.presentation.theme.WineBerry2
import kotlinx.collections.immutable.PersistentList
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterScreen(
    onMovieClick: (String) -> Unit,
    viewModel: FiltersViewModel = koinViewModel()
) {
    val movies by viewModel.moviesList.collectAsStateWithLifecycle()
    val genres by viewModel.genresList.collectAsStateWithLifecycle()
    val selectedGenreId by viewModel.selectedGenreId.collectAsStateWithLifecycle()
    val rangeValues by viewModel.rangeValues.collectAsStateWithLifecycle()

    FilterContent(
        movies = movies,
        genres = genres,
        selectedGenreId = selectedGenreId,
        rangeValues = rangeValues,
        onGenreSelected = { viewModel.onGenreSelected(it) },
        onSliderChange = { viewModel.onSliderRangeChanged(it) },
        onSearchClick = { viewModel.searchButtonClicked() },
        onMovieClick = onMovieClick,
        onFavouriteClick = { viewModel.favouriteIconClicked(it) },
        onLoadMore = { viewModel.listEndReached() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownGenreSelector(
    expanded: Boolean,
    selectedOption: String,
    options: PersistentList<GenreDataModel>,
    onExpandedChange: (Boolean) -> Unit,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { onExpandedChange(it) },
        modifier = modifier
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

@Composable
fun FilterHeader(
    genres: PersistentList<GenreDataModel>,
    selectedGenreId: Int?,
    rangeValues: List<Float>,
    onGenreSelected: (Int?) -> Unit,
    onSliderChange: (List<Float>) -> Unit,
    onSearchClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val selectedOption by remember(selectedGenreId, genres) {
        derivedStateOf {
            genres.find { it.id == selectedGenreId }?.name ?: "Choose genre"
        }
    }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shadowElevation = 4.dp,
        color = WineBerry2
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Genre",
                    color = Gold,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.weight(1f)
                )

                DropdownGenreSelector(
                    expanded = expanded,
                    selectedOption = selectedOption,
                    options = genres,
                    onExpandedChange = { expanded = it },
                    onOptionSelected = { genreName ->
                        val selectedGenre = genres.find { it.name == genreName }
                        onGenreSelected(selectedGenre?.id)
                        expanded = false
                    },
                    modifier = Modifier.weight(2f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            RatingRangePicker(
                rangeValues = rangeValues,
                onValueChange = onSliderChange
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onSearchClick,
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Gold,
                    contentColor = WineBerry2
                )
            ) {
                Text(text = "SEARCH", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun RatingRangePicker(
    rangeValues: List<Float>,
    onValueChange: (List<Float>) -> Unit
) {
    Column {
        Text(
            text = "Rating: ${"%.1f".format(rangeValues[0])} - ${"%.1f".format(rangeValues[1])}",
            color = Gold,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
        )
        RangeSlider(
            value = rangeValues[0]..rangeValues[1],
            onValueChange = { onValueChange(listOf(it.start, it.endInclusive)) },
            valueRange = 0f..10f,
            colors = SliderDefaults.colors(
                thumbColor = Gold,
                activeTrackColor = Gold,
                inactiveTrackColor = White.copy(alpha = 0.24f)
            )
        )
    }
}

@Composable
fun FilterContent(
    movies: PersistentList<MovieDataModel>,
    genres: PersistentList<GenreDataModel>,
    selectedGenreId: Int?,
    rangeValues: List<Float>,
    onGenreSelected: (Int?) -> Unit,
    onSliderChange: (List<Float>) -> Unit,
    onSearchClick: () -> Unit,
    onMovieClick: (String) -> Unit,
    onFavouriteClick: (MovieDataModel) -> Unit,
    onLoadMore: () -> Unit
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    var showPlaceholder by remember { mutableStateOf(false) }

    LaunchedEffect(movies.isEmpty()) {
        if (movies.isEmpty()) {
            showPlaceholder = false
            delay(4000)
            showPlaceholder = true
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        FilterHeader(
            genres = genres,
            selectedGenreId = selectedGenreId,
            rangeValues = rangeValues,
            onGenreSelected = onGenreSelected,
            onSliderChange = onSliderChange,
            onSearchClick = {
                onSearchClick()
                coroutineScope.launch { listState.animateScrollToItem(0) }
            }
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Tolopea)
        ) {
            if (movies.isEmpty()) {
                if (showPlaceholder) EmptyMoviesPlaceholder() else LoadingScreen()
            } else {
                FilterResultsList(
                    movies = movies,
                    listState = listState,
                    onMovieClick = onMovieClick,
                    onFavouriteClick = onFavouriteClick,
                    onLoadMore = onLoadMore
                )
            }
        }
    }
}

@Composable
fun FilterResultsList(
    movies: PersistentList<MovieDataModel>,
    listState: LazyListState,
    onMovieClick: (String) -> Unit,
    onFavouriteClick: (MovieDataModel) -> Unit,
    onLoadMore: () -> Unit
) {
    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
                ?: return@derivedStateOf false

            lastVisibleItem.index >= listState.layoutInfo.totalItemsCount - 5
        }
    }

    LaunchedEffect(shouldLoadMore.value) {
        if (shouldLoadMore.value && movies.isNotEmpty()) {
            onLoadMore()
        }
    }

    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(16.dp)
    ) {
        items(
            items = movies,
            key = { it.movieID }
        ) { movie ->
            MovieItem(
                movie = movie,
                onItemClick = { onMovieClick(it.movieID) },
                onFavouriteClick = onFavouriteClick
            )
        }
    }
}
