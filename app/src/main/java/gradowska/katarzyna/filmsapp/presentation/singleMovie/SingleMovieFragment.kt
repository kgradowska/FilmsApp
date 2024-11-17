package gradowska.katarzyna.filmsapp.presentation.singleMovie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import gradowska.katarzyna.filmsapp.databinding.FragmentSingleMovieBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SingleMovieFragment : Fragment() {

    private var _binding: FragmentSingleMovieBinding? = null
    private val binding get() = _binding!!

    private val args: SingleMovieFragmentArgs by navArgs()

    private val viewModel: SingleMovieViewModel by viewModel { parametersOf(args.idMovie) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSingleMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
        checkVisibility()
        binding.singleMovieTitleText.text = args.idMovie
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun checkVisibility() {
        if (binding.tagLine.text == "    ") {
            binding.tagLine.visibility = View.GONE
        }
    }

    private fun observe() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movieDetails.collect {
                    binding.singleMovieTitleText.text = it.movieTitle
                    binding.singleMovieRate.text = String.format("%.2f", it.movieRate.toDouble())
                    binding.singleMovieBodyText.text = it.movieDescription
                    Glide.with(binding.root.context).load(it.moviePhoto)
                        .into(binding.singleMovieImage)
                    binding.yearOfProduction.text = it.movieReleaseDate
                    binding.viewsCounter.text = it.movieVoteCount
                    binding.genre.text = it.movieGenres
                    binding.runtime.text = it.movieRuntime
                    binding.productionCountries.text = it.movieProductionCountries
                    Glide.with(binding.root.context).load(it.movieBackdropPath)
                        .into(binding.movieBackdrop)
                    binding.tagLine.text = it.movieTagline
                    binding.budget.text = it.movieBudget
                    binding.revenue.text = it.movieRevenue
                    binding.originalTitle.text = it.movieOriginalTitle
                    binding.originalLanguage.text = it.movieOriginalLanguage
                }
            }
        }
    }
}
