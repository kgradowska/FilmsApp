package gradowska.katarzyna.filmsapp.presentation.recyclerList

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import gradowska.katarzyna.filmsapp.databinding.ItemMovieBinding
import gradowska.katarzyna.filmsapp.presentation.movie.MovieDataModel

class MovieViewHolder(
    private val binding: ItemMovieBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: MovieDataModel, clickListener: ((MovieDataModel) -> Unit)?) {
        with(movie) {
            binding.titleText.text = movieTitle
            binding.bodyText.text = movieDescription
            binding.rate.text = movieRate
            Glide.with(binding.root.context).load(moviePhoto).into(binding.movieImage)

            binding.root.setOnClickListener {
                clickListener?.invoke(movie)
            }
        }
    }
}