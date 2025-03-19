package gradowska.katarzyna.filmsapp.presentation.moviesgenres

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gradowska.katarzyna.filmsapp.databinding.ItemMovieBinding
import gradowska.katarzyna.filmsapp.databinding.PlaceholderEmptyListBinding
import gradowska.katarzyna.filmsapp.domain.entity.MovieDataModel

class MoviesGenresAdapter() : RecyclerView.Adapter<MoviesGenresViewHolder>() {

    private val moviesList: ArrayList<MovieDataModel> = arrayListOf()
    var clickListener: ((MovieDataModel) -> Unit)? = null
    var favouriteIconClickListener: ((MovieDataModel) -> Unit)? = null

    fun setItems(newList: List<MovieDataModel>) {
        moviesList.clear()
        moviesList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesGenresViewHolder {
        return when (viewType) {
            ITEM_TYPE -> {
                val binding =
                    ItemMovieBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                MoviesGenresItemViewHolder(binding)
            }

            else -> {
                val binding =
                    PlaceholderEmptyListBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                MoviesGenresEmptyViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: MoviesGenresViewHolder, position: Int) {
        (holder as? MoviesGenresItemViewHolder)?.bind(
            moviesList[position],
            clickListener,
            favouriteIconClickListener
        )
    }

    override fun getItemViewType(position: Int): Int {
        return if (moviesList.isNotEmpty()) {
            ITEM_TYPE
        } else {
            PLACEHOLDER_TYPE
        }
    }

    override fun getItemCount(): Int = if (moviesList.isNotEmpty()) {
        moviesList.size
    } else {
        1
    }

    companion object {
        private const val PLACEHOLDER_TYPE = 0
        private const val ITEM_TYPE = 1
    }
}