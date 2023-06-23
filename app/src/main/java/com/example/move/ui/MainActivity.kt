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
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.Worker
import androidx.work.WorkerParameters
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
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: ExercisesViewModel
    lateinit var workoutHistoryViewModel: WorkoutHistoryViewModel

    lateinit var repository: ExercisesRepository

    lateinit var sharedPref: SharedPreferences

    val saveRequest = PeriodicWorkRequestBuilder<UploadWorker>(12, TimeUnit.HOURS).build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNavigation()

        sharedPref = this.getPreferences(Context.MODE_PRIVATE)

        repository = ExercisesRepository(ExerciseDatabase(this))

        val viewModelProvideFactory = ExercisesViewModelProvideFactory(repository)

        val otherFactory = WorkoutHistoryViewModelProvideFactory(repository)

        viewModel = ViewModelProvider(this, viewModelProvideFactory)[ExercisesViewModel::class.java]

        workoutHistoryViewModel =
            ViewModelProvider(this, otherFactory)[WorkoutHistoryViewModel::class.java]

        if (!apiCallSaved()) {
            populateDatabase(repository)
        }

        // changeBottomNavigationVisibilityDependingOnFragment()
    }

    private fun setupBottomNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.exerciseNavHostFragment) as NavHostFragment

        binding.bottomNavigationView.setupWithNavController(navHostFragment.findNavController())

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

    private fun apiCallSaved(): Boolean = sharedPref.getBoolean(isApiCallSaved, false)

//    private fun changeBottomNavigationVisibilityDependingOnFragment() {
//
//    }

    private fun populateDatabase(repository: ExercisesRepository) {
        lifecycleScope.launch {
            repository.deleteAllExercises()
            val response = repository.getExercisesFromApi()

            response.body()?.let {
                repository.upsertAllExercises(it)
            }
        }

        sharedPref.edit().putBoolean(isApiCallSaved, true).apply()
    }

    inner class UploadWorker(appContext: Context, workerParams: WorkerParameters) :
        Worker(appContext, workerParams) {
        override fun doWork(): Result {
            populateDatabase(repository)

            return Result.success()
        }
    }

    companion object {
        const val isApiCallSaved = "apiCallSaved"
    }
}