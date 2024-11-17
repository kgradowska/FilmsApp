package gradowska.katarzyna.filmsapp.presentation.recyclerList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import gradowska.katarzyna.filmsapp.databinding.FragmentRecyclerListBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesFragment : Fragment() {

    private var _binding: FragmentRecyclerListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MoviesFragmentViewModel by viewModel()

    private val adapter = MovieAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecyclerListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        observe()
        showToast()
        searchButtonClicked()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showToast() {
        Toast.makeText(context, "Click on a movie to find out more.", Toast.LENGTH_LONG).show()
    }

    private fun observe() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.moviesList.collect {
                    adapter.setItems(it)
                }
            }
        }
    }

    private fun searchButtonClicked() {
        binding.button.setOnClickListener {
            viewModel.searchClicked(binding.search.text.toString())
        }
    }


    private fun initRecyclerView() {
        adapter.clickListener = {
            Log.d("Adapter", "Kliknięty data model: $it")
            findNavController().navigate(
                MoviesFragmentDirections.actionRecyclerListFragmentToSingleMovieFragment(
                    it.movieID
                )
            )
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