package gradowska.katarzyna.filmsapp.presentation.moviesgenres

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import gradowska.katarzyna.filmsapp.databinding.FragmentGenresMoviesBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class MoviesGenresFragment : Fragment() {

    private var _binding: FragmentGenresMoviesBinding? = null
    private val binding get() = _binding!!

    private val args: MoviesGenresFragmentArgs by navArgs()

    private val viewModel: MoviesGenresViewModel by viewModel { parametersOf(args.idGenre) }

    private val adapter = MoviesGenresAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGenresMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        viewModel
        observe()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initRecyclerView() {
        binding.filmRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.filmRecyclerView.adapter = adapter

        adapter.clickListener = {
            findNavController().navigate(
                MoviesGenresFragmentDirections.actionMoviesGenresFragmentToSingleMovieFragment(it.movieID)
            )
        }
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

}