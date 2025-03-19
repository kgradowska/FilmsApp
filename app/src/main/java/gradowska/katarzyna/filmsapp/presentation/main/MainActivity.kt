package gradowska.katarzyna.filmsapp.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import gradowska.katarzyna.filmsapp.R
import gradowska.katarzyna.filmsapp.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            this.supportFragmentManager.findFragmentById(R.id.fragment_container) as? NavHostFragment
        val controller = navHostFragment?.navController ?: return
        binding.bottomNavigationView.setupWithNavController(
            navController = controller,
        )
    }
}