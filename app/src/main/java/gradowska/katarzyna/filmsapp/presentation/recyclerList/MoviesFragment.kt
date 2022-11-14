package gradowska.katarzyna.filmsapp.presentation.recyclerList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import gradowska.katarzyna.filmsapp.databinding.FragmentRecyclerListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesFragment : Fragment() {

    private var _binding: FragmentRecyclerListBinding? = null
    val binding get() = _binding!!

    private val viewModel: MoviesFragmentViewModel by viewModel()

    private val adapter = MovieAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecyclerListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        observe()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observe() {
        lifecycleScope.launchWhenStarted {
            viewModel.moviesList.collect {
                adapter.setItems(it)
            }
        }
    }

    private fun initRecyclerView() {
        adapter.clickListener = {
            Log.d("Adapter", "Kliknięty data model: $it")
        }
        adapter.favouriteIconClickListener = {
            viewModel.favouriteIconClicked(it) // modyfikacja istniejacej listy - zmiana flagi movieLiked
        }

        binding.filmRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollHorizontally(1)) {
                    viewModel.recyclerEndReached()
                }
            }
        })


        binding.filmRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.filmRecyclerView.adapter = adapter
    }
}