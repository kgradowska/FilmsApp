package gradowska.katarzyna.filmsapp.presentation.recyclerList

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import gradowska.katarzyna.filmsapp.R
import gradowska.katarzyna.filmsapp.databinding.ItemMovieBinding
import gradowska.katarzyna.filmsapp.domain.entity.MovieDataModel


class MovieViewHolder(
    private val binding: ItemMovieBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        movie: MovieDataModel,
        clickListener: ((MovieDataModel) -> Unit)?,
        favouriteIconClickListener: ((MovieDataModel) -> Unit)?
    ) {
        with(movie) {
            binding.titleText.text = movieTitle
            binding.bodyText.text = movieDescription
            if (movieRate == null) {
                binding.rate.isVisible = false
            } else {
                binding.rate.isVisible = true
                binding.rate.text = roundTo3Digits(movieRate)
            }

            Glide.with(binding.root.context)
                .load(moviePhoto)
                .placeholder(R.drawable.ic_poster_placeholder)
                .into(binding.movieImage)

            if (movieLiked) {
                binding.starBorder.setImageResource(R.drawable.ic_baseline_star_rate_24)
            } else {
                binding.starBorder.setImageResource(R.drawable.ic_baseline_star_border_24)
            }

            binding.starBorder.setOnClickListener {
                favouriteIconClickListener?.invoke(movie)
            }

            binding.root.setOnClickListener {
                clickListener?.invoke(movie)
            }
        }
    }

    private fun roundTo3Digits(number: Double): String {
        val number3digits: Double = String.format("%.3f", number).toDouble()
        val number2digits: String = String.format("%.2f", number3digits).toDouble().toString()
        return number2digits
    }
}