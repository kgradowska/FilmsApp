package gradowska.katarzyna.filmsapp.presentation.searchMovies

import android.widget.Toast
import androidx.compose.foundation.background
import gradowska.katarzyna.filmsapp.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import gradowska.katarzyna.filmsapp.domain.entity.MovieDataModel
import gradowska.katarzyna.filmsapp.presentation.shared.EmptyMoviesPlaceholder
import gradowska.katarzyna.filmsapp.presentation.shared.LoadingScreen
import gradowska.katarzyna.filmsapp.presentation.shared.MovieItem
import gradowska.katarzyna.filmsapp.presentation.theme.Tolopea
import kotlinx.collections.immutable.PersistentList
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun MoviesScreen(
    onMovieClick: (String) -> Unit,
    viewModel: SearchMoviesViewModel = koinViewModel()
) {
    val movies by viewModel.moviesList.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val toastText = stringResource(R.string.movie_click_toast)

    var searchQuery by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.showToast.collect {
            Toast.makeText(
                context,
                toastText,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    MoviesContent(
        searchQuery = searchQuery,
        movies = movies,
        onSearchQueryChange = { searchQuery = it },
        onSearchAction = { viewModel.searchClicked(searchQuery) },
        onMovieClick = onMovieClick,
        onFavouriteClick = { viewModel.favouriteIconClicked(it) },
        onLoadMore = { viewModel.recyclerEndReached() }
    )
}

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearchAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(R.string.clear_search)
                    )
                }
            }
        },
        modifier = modifier.fillMaxWidth(),
        placeholder = { Text(text = stringResource(R.string.search_movie)) },
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearchAction()
                keyboardController?.hide()
            }
        )
    )
}

@Composable
fun MoviesContent(
    searchQuery: String,
    movies: PersistentList<MovieDataModel>,
    onSearchQueryChange: (String) -> Unit,
    onSearchAction: () -> Unit,
    onMovieClick: (String) -> Unit,
    onFavouriteClick: (MovieDataModel) -> Unit,
    onLoadMore: () -> Unit
) {
    var showPlaceholder by remember { mutableStateOf(false) }

    LaunchedEffect(movies.isEmpty()) {
        if (movies.isEmpty()) {
            showPlaceholder = false
            delay(4000)
            showPlaceholder = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Tolopea)
    ) {
        SearchBar(
            query = searchQuery,
            onQueryChange = onSearchQueryChange,
            onSearchAction = onSearchAction,
            modifier = Modifier.padding(16.dp)
        )

        if (movies.isEmpty()) {
            if (showPlaceholder) EmptyMoviesPlaceholder() else LoadingScreen()
        } else {
            MoviesList(
                movies = movies,
                onMovieClick = onMovieClick,
                onFavouriteClick = onFavouriteClick,
                onLoadMore = onLoadMore
            )
        }
    }
}

@Composable
fun MoviesList(
    movies: PersistentList<MovieDataModel>,
    onMovieClick: (String) -> Unit,
    onFavouriteClick: (MovieDataModel) -> Unit,
    onLoadMore: () -> Unit
) {
    val listState = rememberLazyListState()

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

    LazyColumn(state = listState) {
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
