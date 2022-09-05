package gradowska.katarzyna.filmsapp.presentation.singleMovie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import gradowska.katarzyna.filmsapp.databinding.FragmentSingleMovieBinding

class SingleMovieFragment : Fragment() {

    private var _binding: FragmentSingleMovieBinding? = null
    val binding get() = _binding!!

    private val viewModel: SingleMovieViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSingleMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
