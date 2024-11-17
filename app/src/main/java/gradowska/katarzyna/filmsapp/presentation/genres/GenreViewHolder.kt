package gradowska.katarzyna.filmsapp.presentation.genres

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import gradowska.katarzyna.filmsapp.databinding.ItemGenreBinding
import gradowska.katarzyna.filmsapp.domain.entity.GenreDataModel

class GenreViewHolder(
    private val binding: ItemGenreBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(genre: GenreDataModel, isLast: Boolean) {
        binding.genre.text = genre.name
        binding.line.isVisible = !isLast
    }
}
