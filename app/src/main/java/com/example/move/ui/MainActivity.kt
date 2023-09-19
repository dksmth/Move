package com.example.move.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.move.R
import com.example.move.databinding.ActivityMainBinding
import com.example.move.ui.fragments.ExerciseInfoFragment
import com.example.move.ui.fragments.WorkoutFinishedFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.exerciseNavHostFragment) as NavHostFragment

        val navView = binding.bottomNavigationView

        navView.setupWithNavController(navHostFragment.navController)

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