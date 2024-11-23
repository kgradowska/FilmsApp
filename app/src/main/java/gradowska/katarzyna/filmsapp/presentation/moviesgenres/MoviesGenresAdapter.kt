package gradowska.katarzyna.filmsapp.presentation.moviesgenres

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gradowska.katarzyna.filmsapp.databinding.ItemMovieBinding
import gradowska.katarzyna.filmsapp.domain.entity.MovieDataModel

class MoviesGenresAdapter() : RecyclerView.Adapter<MoviesGenresViewHolder>() {
    private val moviesList: ArrayList<MovieDataModel> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesGenresViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesGenresViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MoviesGenresViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = moviesList.size

}