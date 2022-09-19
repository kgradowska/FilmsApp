package gradowska.katarzyna.filmsapp.presentation.recyclerList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gradowska.katarzyna.filmsapp.databinding.ItemMovieBinding
import gradowska.katarzyna.filmsapp.presentation.movie.MovieDataModel

class MovieAdapter() : RecyclerView.Adapter<MovieViewHolder>() {
    private val formats: ArrayList<MovieDataModel> = arrayListOf()

    fun setItems(newList: List<MovieDataModel>) { // jestem tylko glupim adapterem, nie wiem co to za lista ani skad, dali to pokazuje
        formats.clear()
        formats.addAll(newList)
        notifyDataSetChanged() // hej, zmienily sie dane, odswiez sobie widoki -> wola onBindViewHolder!!
    }

    var clickListener: ((MovieDataModel) -> Unit)? = null
    var favouriteIconClickListener: ((MovieDataModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(formats[position], clickListener, favouriteIconClickListener)
    }

    override fun getItemCount() = formats.size
}