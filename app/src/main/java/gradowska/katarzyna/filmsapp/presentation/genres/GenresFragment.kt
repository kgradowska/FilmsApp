package gradowska.katarzyna.filmsapp.presentation.genres

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import gradowska.katarzyna.filmsapp.databinding.FragmentGenresBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class GenresFragment : Fragment() {

    private var _binding: FragmentGenresBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GenresFragmentViewModel by viewModel()

    private val adapter = GenreAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGenresBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        observe()
        //check visibility?
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observe() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.genresList.collect {
                    adapter.setItems(it)
                }
            }
        }
    }

    private fun initRecyclerView() {
        binding.genresRecycler.layoutManager = LinearLayoutManager(context)
        binding.genresRecycler.adapter = adapter

        /*adapter.clickListener = {
            findNavController().navigate(
                GenresFragmentDirections.actionGenresFragmentToMoviesGenresFragment(it.id)
            )
        }*/

    }
}