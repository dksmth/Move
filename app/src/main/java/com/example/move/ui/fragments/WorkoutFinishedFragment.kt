package com.example.move.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.move.databinding.FragmentWorkoutFinishedBinding

class WorkoutFinishedFragment : Fragment() {

    private var _binding: FragmentWorkoutFinishedBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentWorkoutFinishedBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btGoToExercises.setOnClickListener {
            navigateToExercises()
        }

    }

    private fun navigateToExercises() {
        findNavController().navigate(
            WorkoutFinishedFragmentDirections.actionWorkoutFinishedFragmentToExerciseListFragment()
        )
    }
}