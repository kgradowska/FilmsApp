package gradowska.katarzyna.filmsapp.presentation.recyclerList.compose

import androidx.compose.foundation.background
import gradowska.katarzyna.filmsapp.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import gradowska.katarzyna.filmsapp.presentation.recyclerList.MoviesViewModel
import gradowska.katarzyna.filmsapp.presentation.theme.Tolopea
import org.koin.androidx.compose.koinViewModel

@Composable
fun MoviesScreen(
    onMovieClick: (String) -> Unit,
    viewModel: MoviesViewModel = koinViewModel()
) {
    val movies by viewModel.moviesList.collectAsStateWithLifecycle()
    val keyboardController = LocalSoftwareKeyboardController.current

    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Tolopea)
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(
                        onClick = { searchQuery = "" }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Clear search"
                        )
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            placeholder = { Text(text = stringResource(R.string.search_movie)) },
            readOnly = false,
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
                    viewModel.searchClicked(searchQuery)
                    keyboardController?.hide()
                }
            )
        )

        LazyColumn {
            itemsIndexed(
                movies,
            ) { index, movie ->
                MovieItem(
                    movie = movie,
                    onItemClick = { clickedMovie ->
                        onMovieClick(clickedMovie.movieID)
                    },
                    onFavouriteClick = { favouriteMovie ->
                        viewModel.favouriteIconClicked(favouriteMovie)
                    }
                )

                if (index == movies.lastIndex) {
                    LaunchedEffect(index) {
                        viewModel.recyclerEndReached()
                    }
                }
            }
        }
    }
}
