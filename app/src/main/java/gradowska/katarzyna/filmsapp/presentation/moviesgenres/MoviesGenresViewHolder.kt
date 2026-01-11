package gradowska.katarzyna.filmsapp.presentation.moviesgenres

import android.view.View
import androidx.compose.ui.Modifier
import androidx.recyclerview.widget.RecyclerView
import gradowska.katarzyna.filmsapp.databinding.ItemMovieBinding
import gradowska.katarzyna.filmsapp.databinding.PlaceholderEmptyListBinding
import gradowska.katarzyna.filmsapp.domain.entity.MovieDataModel
import gradowska.katarzyna.filmsapp.presentation.recyclerList.compose.MovieItem

abstract class MoviesGenresViewHolder(val view: View) : RecyclerView.ViewHolder(view)

class MoviesGenresItemViewHolder(
    private val binding: ItemMovieBinding
) : MoviesGenresViewHolder(binding.root) {

    fun bind(
        movie: MovieDataModel,
        clickListener: ((MovieDataModel) -> Unit)?,
        favouriteIconClickListener: ((MovieDataModel) -> Unit)?
    ) {
        binding.movieItemCompose.setContent {
            MovieItem(
                titleText = movie.movieTitle,
                bodyText = movie.movieDescription,
                rate = movie.movieRate,
                movieImage = movie.moviePhoto,
                isLiked = movie.movieLiked,
                onItemClick = {
                    clickListener?.invoke(movie)
                },
                onFavouriteClick = {
                    favouriteIconClickListener?.invoke(movie)
                },
                modifier = Modifier
            )
        }
    }

    private fun roundTo3Digits(number: Double): String {
        return String.format("%.2f", number).replace(",", ".")
    }
}

class MoviesGenresEmptyViewHolder(
    binding: PlaceholderEmptyListBinding
) : MoviesGenresViewHolder(binding.root)