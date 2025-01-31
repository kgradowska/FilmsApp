package gradowska.katarzyna.filmsapp.presentation.recyclerList

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import gradowska.katarzyna.filmsapp.R
import gradowska.katarzyna.filmsapp.databinding.FragmentRecyclerListBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesFragment : Fragment() {

    private var _binding: FragmentRecyclerListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MoviesViewModel by viewModel()

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
        searchButtonClicked()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showToast() {
        Toast.makeText(context, R.string.movie_click_toast, Toast.LENGTH_LONG).show()
    }

    private fun observe() {
        lifecycleScope.launch {
            viewModel.moviesList.collect {
                adapter.setItems(it)
            }
        }

        lifecycleScope.launch {
            viewModel.showToast.collect {
                showToast()
            }
        }
    }

    private fun searchButtonClicked() {
        binding.search.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.searchClicked(binding.search.text.toString())
                val imm =
                    context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.search.windowToken, 0)
            }
            true
        }
    }


    private fun initRecyclerView() {
        adapter.clickListener = {
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
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.recyclerEndReached()
                }
            }
        })

        binding.filmRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.filmRecyclerView.adapter = adapter
    }
}