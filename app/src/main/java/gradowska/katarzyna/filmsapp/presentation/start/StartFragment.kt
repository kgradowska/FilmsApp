package gradowska.katarzyna.filmsapp.presentation.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import gradowska.katarzyna.filmsapp.R
import gradowska.katarzyna.filmsapp.databinding.FragmentStartBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class StartFragment : Fragment() {

    private var _binding: FragmentStartBinding? = null
    val binding get() = _binding!!

    private val viewModel: StartFragmentViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //initClickListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /*    private fun initClickListener() {
            binding.startButton.setOnClickListener {
                findNavController().navigate(R.id.action_startFragment_to_recyclerListFragment)
            }
            binding.genreButton.setOnClickListener {
                findNavController().navigate(R.id.action_startFragment_to_genresFragment)
            }
        }*/
}


