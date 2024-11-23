package gradowska.katarzyna.filmsapp.presentation.moviesgenres

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import gradowska.katarzyna.filmsapp.databinding.FragmentGenresMoviesBinding
import gradowska.katarzyna.filmsapp.presentation.genres.GenresFragmentDirections
import gradowska.katarzyna.filmsapp.presentation.recyclerList.MoviesFragmentDirections
import org.koin.androidx.viewmodel.ext.android.viewModel


class MoviesGenresFragment : Fragment() {

    private var _binding: FragmentGenresMoviesBinding? = null
    private val binding get() = _binding!!

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
        initRecyclerView()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initRecyclerView() {
//        adapter.clickListener = {
//            findNavController().navigate(
//                GenresFragmentDirections.actionGenresFragmentToMoviesGenresFragment()
//            )
//        }
    }

}