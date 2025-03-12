package gradowska.katarzyna.filmsapp.presentation.singleMovie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.bundle.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import gradowska.katarzyna.filmsapp.R
import gradowska.katarzyna.filmsapp.databinding.FragmentSingleMovieBinding
import gradowska.katarzyna.filmsapp.domain.entity.MovieDetailsDataModel
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
                    if (it == null) return@collect
                    with(binding) {
                        singleMovieTitleText.text = it.movieTitle
                        singleMovieRate.text = String.format("%.2f", it.movieRate.toDouble())
                        singleMovieBodyText.text = it.movieDescription
                        Glide.with(root.context)
                            .load(it.moviePhoto)
                            .placeholder(R.drawable.ic_poster_placeholder)
                            .into(singleMovieImage)
                        yearOfProduction.text = it.movieReleaseDate
                        viewsCounter.text = it.movieVoteCount
                        genre.text = it.movieGenres
                        runtime.text = it.movieRuntime
                        productionCountries.text = it.movieProductionCountries
                        productionCountriesText.isVisible = it.movieProductionCountries.isNotBlank()

                        Glide.with(root.context)
                            .load(it.movieBackdropPath)
                            .placeholder(R.drawable.ic_poster_placeholder)
                            .into(movieBackdrop)

                        tagLine.isVisible = it.movieTagline.isNotBlank()
                        tagLine.text = it.movieTagline

                        if (it.movieBudget == "0 \$" && it.movieRevenue == "0 \$") {
                            constraint.isVisible = false
                            budget.text = it.movieBudget
                            revenue.text = it.movieRevenue
                        } else if (it.movieBudget == "0 \$" && it.movieRevenue != "0 \$") {
                            budget.isVisible = false
                            budgetText.isVisible = false
                            revenue.text = it.movieRevenue
                        } else if (it.movieBudget != "0 \$" && it.movieRevenue == "0 \$") {
                            revenue.isVisible = false
                            revenueText.isVisible = false
                            budget.text = it.movieBudget
                        } else {
                            constraint.isVisible = true
                            budget.text = it.movieBudget
                            revenue.text = it.movieRevenue
                        }

                        originalTitle.text = it.movieOriginalTitle
                        originalTitleText.isVisible = it.movieOriginalTitle.isNotBlank()
                        originalLanguage.text = it.movieOriginalLanguage
                        originalLanguageText.isVisible = it.movieOriginalLanguage.isNotBlank()

                        if (it.movieLiked) {
                            starBorder.setImageResource(R.drawable.ic_baseline_star_rate_24)
                        } else {
                            starBorder.setImageResource(R.drawable.ic_baseline_star_border_24)
                        }

                        setFragmentResult(
                            MOVIE_FRAGMENT_RESULT,
                            bundleOf(
                                MOVIE_FRAGMENT_FAVOURITE_KEY to it.movieLiked,
                                MOVIE_FRAGMENT_ID_KEY to it.movieID,
                            )
                        )

                        starBorder.setOnClickListener { _ ->
                            viewModel.favouriteIconClicked(it) // modyfikacja istniejacej listy - zmiana flagi movieLiked
                        }

                        viewsText.text =
                            resources.getQuantityText(R.plurals.votes, it.movieVoteCount.toInt())
                    }
                }
            }
        }
    }

    companion object {
        const val MOVIE_FRAGMENT_RESULT = "MOVIE_FRAGMENT_RESULT"
        const val MOVIE_FRAGMENT_FAVOURITE_KEY = "MOVIE_FRAGMENT_FAVOURITE_KEY"
        const val MOVIE_FRAGMENT_ID_KEY = "MOVIE_FRAGMENT_ID_KEY"
    }
}
