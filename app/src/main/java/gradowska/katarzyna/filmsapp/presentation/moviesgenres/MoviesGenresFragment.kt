package gradowska.katarzyna.filmsapp.presentation.moviesgenres

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import gradowska.katarzyna.filmsapp.databinding.FragmentGenresMoviesBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class MoviesGenresFragment : Fragment() {

    private var _binding: FragmentGenresMoviesBinding? = null
    private val binding get() = _binding!!

    private val args: MoviesGenresFragmentArgs by navArgs()

    private val viewModel: MoviesGenresViewModel by viewModel()

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
        setClickListeners()
        initRecyclerView()
        observeMovies()
        observeGenresArray()
        observeAppBarHiding()
    }

    private fun observeAppBarHiding() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.hideAppBarLayout.collectLatest {
                hideAppBarLayout()
            }
        }
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

        adapter.favouriteIconClickListener = {
            viewModel.favouriteIconClicked(it)
        }
    }

    private fun hideAppBarLayout() {
        binding.appBar.setExpanded(false, true)
    }

    private fun observeMovies() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.moviesList.collect {
                    adapter.setItems(it)
                    binding.slider.setLabelFormatter { value ->
                        if (value % 1 == 0f) {
                            value.toInt().toString()
                        } else {
                            String.format("%.1f", value)
                        }
                    }
                }
            }
        }
    }

    private fun setClickListeners() {
        binding.button.setOnClickListener {
            with(viewModel) {
                spinnerValues = binding.slider.values
                lifecycleScope.launch {
                    searchButtonClicked()
                }
            }
        }
    }

    private fun handleSpinner(values: Array<String>) {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, values)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.genres.adapter = adapter

        lifecycleScope.launch {
            viewModel.genresList.collect { genres ->
                val genre = genres.find { it.id == viewModel.genreId }
                if (genre != null) {
                    val defaultPosition = values.indexOf(genre.name)
                    if (defaultPosition != -1) {
                        binding.genres.setSelection(defaultPosition)
                    }
                }
            }
        }

        binding.genres.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = parent.getItemAtPosition(position).toString()

                lifecycleScope.launch {
                    viewModel.genresList.collect { genres ->
                        val genre = genres.find { it.name == selectedItem }
                        if (genre != null) {
                            viewModel.genreId = genre.id
                        }
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // No action needed
            }

        }
    }

    private fun observeGenresArray() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.genresList.collect { genresList ->
                    val genresArray = genresList.map { it.name }.toTypedArray()
                    handleSpinner(genresArray)
                }
            }
        }
    }
}