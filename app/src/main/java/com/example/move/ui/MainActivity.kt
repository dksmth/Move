package com.example.move.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.move.R
import com.example.move.databinding.ActivityMainBinding
import com.example.move.db.ExerciseDatabase
import com.example.move.repo.ExercisesRepository
import com.example.move.ui.fragments.ExerciseInfoFragment
import com.example.move.ui.fragments.WorkoutFinishedFragment
import com.example.move.ui.viewmodels.ExercisesViewModel
import com.example.move.ui.viewmodels.ExercisesViewModelProvideFactory
import com.example.move.ui.viewmodels.WorkoutHistoryViewModel
import com.example.move.ui.viewmodels.WorkoutHistoryViewModelProvideFactory
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: ExercisesViewModel
    lateinit var workoutHistoryViewModel: WorkoutHistoryViewModel

    private val sharedPref: SharedPreferences? by lazy {
        this.getPreferences(Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNavigation()

        val repository = ExercisesRepository(ExerciseDatabase(this))
        val viewModelProvideFactory = ExercisesViewModelProvideFactory(repository)

        val otherFactory = WorkoutHistoryViewModelProvideFactory(repository)

        viewModel = ViewModelProvider(this, viewModelProvideFactory)[ExercisesViewModel::class.java]
        workoutHistoryViewModel =
            ViewModelProvider(this, otherFactory)[WorkoutHistoryViewModel::class.java]


        val exercisesRepository = ExercisesRepository(db = ExerciseDatabase.invoke(this))

        if (apiCallNotSaved()) {
            populateDatabase(repository, exercisesRepository)
        }

        registerCallbackForWorkoutFinishedFragment()
    }

    private fun setupBottomNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.exerciseNavHostFragment) as NavHostFragment

        binding.bottomNavigationView.setupWithNavController(navHostFragment.findNavController())
    }

    private fun apiCallNotSaved(): Boolean = sharedPref?.getBoolean(isApiCallSaved, false) ?: false

    private fun registerCallbackForWorkoutFinishedFragment() {
        supportFragmentManager.registerFragmentLifecycleCallbacks(object :
            FragmentManager.FragmentLifecycleCallbacks() {

            override fun onFragmentViewCreated(
                fm: FragmentManager,
                fragment: Fragment,
                v: View,
                savedInstanceState: Bundle?,
            ) {
                binding.bottomNavigationView.visibility = when (fragment) {
                    is WorkoutFinishedFragment -> View.GONE
                    is ExerciseInfoFragment -> View.GONE
                    else -> View.VISIBLE
                }
            }
        }, true)
    }

    private fun populateDatabase(
        repository: ExercisesRepository,
        exercisesRepository: ExercisesRepository,
    ) {
        lifecycleScope.launch {

            if (!repository.cacheExists()) {
                val response = exercisesRepository.getExercises()

                response.body()?.let {
                    exercisesRepository.upsertAll(it)
                }
            }
        }

        sharedPref?.edit()?.putBoolean(isApiCallSaved, true)?.apply()
    }

    companion object {
        const val isApiCallSaved = "apiCallSaved"
    }
}