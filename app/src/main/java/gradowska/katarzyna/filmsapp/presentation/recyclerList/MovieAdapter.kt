package gradowska.katarzyna.filmsapp.presentation.recyclerList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gradowska.katarzyna.filmsapp.databinding.ItemMovieBinding
import gradowska.katarzyna.filmsapp.domain.entity.MovieDataModel

class MovieAdapter() : RecyclerView.Adapter<MovieViewHolder>() {
    private val moviesList: ArrayList<MovieDataModel> = arrayListOf()

    fun setItems(newList: List<MovieDataModel>) {
        moviesList.clear()
        moviesList.addAll(newList)
        notifyDataSetChanged()
    }

    var clickListener: ((MovieDataModel) -> Unit)? = null
    var favouriteIconClickListener: ((MovieDataModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(moviesList[position], clickListener, favouriteIconClickListener)
    }

    override fun getItemCount() = moviesList.size
}