package gradowska.katarzyna.filmsapp.presentation.genres

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import gradowska.katarzyna.filmsapp.databinding.ItemGenreBinding
import gradowska.katarzyna.filmsapp.domain.entity.GenreDataModel

class GenreViewHolder(
    private val binding: ItemGenreBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        genre: GenreDataModel,
        isLast: Boolean,
        clickListener: ((GenreDataModel) -> Unit)?
    ) {
        binding.genre.text = genre.name
        binding.line.isVisible = !isLast
        binding.root.setOnClickListener {
            clickListener?.invoke(genre)
        }
    }
}
