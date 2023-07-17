package com.example.move.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUiSaveStateControl
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


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: ExercisesViewModel
    lateinit var workoutHistoryViewModel: WorkoutHistoryViewModel

    lateinit var repository: ExercisesRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNavigation()

        repository = ExercisesRepository(ExerciseDatabase(this))

        val viewModelProvideFactory = ExercisesViewModelProvideFactory(repository)

        val otherFactory = WorkoutHistoryViewModelProvideFactory(repository)

        viewModel = ViewModelProvider(this, viewModelProvideFactory)[ExercisesViewModel::class.java]

        workoutHistoryViewModel =
            ViewModelProvider(this, otherFactory)[WorkoutHistoryViewModel::class.java]
    }

    @OptIn(NavigationUiSaveStateControl::class)
    private fun setupBottomNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.exerciseNavHostFragment) as NavHostFragment

        val navView = binding.bottomNavigationView

        NavigationUI.setupWithNavController(navView, navHostFragment.findNavController(), false)

        //.setupWithNavController(navHostFragment.findNavController())

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

}