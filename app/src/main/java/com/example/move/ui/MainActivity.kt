package com.example.move.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.example.move.databinding.ActivityMainBinding
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.move.R
import com.example.move.db.ExerciseDatabase
import com.example.move.repo.ExercisesRepository
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: ExercisesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.exerciseNavHostFragment) as NavHostFragment

        binding.bottomNavigationView.setupWithNavController(navHostFragment.findNavController())

        val repository = ExercisesRepository(ExerciseDatabase(this))
        val viewModelProvideFactory = ExercisesViewModelProvideFactory(repository)

        viewModel = ViewModelProvider(this, viewModelProvideFactory)[ExercisesViewModel::class.java]

//        val exercisesRepository = ExercisesRepository(db = ExerciseDatabase.invoke(this))
//
//        lifecycleScope.launch {
//            val response = exercisesRepository.getExercises()
//
//            response.body()?.forEach {
//                exercisesRepository.upsert(it)
//            }
//        }


    }
}