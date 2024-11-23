package gradowska.katarzyna.filmsapp.presentation.genres

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gradowska.katarzyna.filmsapp.databinding.ItemGenreBinding
import gradowska.katarzyna.filmsapp.domain.entity.GenreDataModel

class GenreAdapter() : RecyclerView.Adapter<GenreViewHolder>() {
    private var genres: ArrayList<GenreDataModel> = arrayListOf()

    var clickListener: ((GenreDataModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val binding = ItemGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GenreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bind(genres[position], position == genres.size - 1, clickListener)
    }

    override fun getItemCount(): Int = genres.size

    fun setItems(newList: List<GenreDataModel>) {
        genres.clear()
        genres.addAll(newList)
        notifyDataSetChanged()
    }
}