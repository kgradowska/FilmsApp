package gradowska.katarzyna.filmsapp.presentation.recyclerList

import androidx.recyclerview.widget.RecyclerView
import gradowska.katarzyna.filmsapp.databinding.ItemMovieBinding
import gradowska.katarzyna.filmsapp.presentation.movie.MovieDataModel

class MovieViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: MovieDataModel){
        with(movie){
            binding.titleText.text = movieTitle
            binding.bodyText.text = movieDescription
            binding.rate.text = movieRate
            binding.movieImage.setImageResource(moviePhoto)
        }
    }
}