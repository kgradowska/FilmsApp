package gradowska.katarzyna.filmsapp.presentation.recyclerList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gradowska.katarzyna.filmsapp.databinding.ItemMovieBinding
import gradowska.katarzyna.filmsapp.presentation.movie.MovieDataModel

class MovieAdapter(private val formats: List<MovieDataModel>) : RecyclerView.Adapter<MovieViewHolder>(){

    var clickListener: ((MovieDataModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(formats[position], clickListener)
    }

    override fun getItemCount() = formats.size
}