package com.example.move.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.move.databinding.FragmentWorkoutFinishedBinding

class WorkoutFinishedFragment : Fragment() {

    private var _binding: FragmentWorkoutFinishedBinding? = null
    private val binding get() = _binding!!

    private val args: WorkoutFinishedFragmentArgs by navArgs()

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
            navigateToWorkoutHistory()
        }

        binding.tvLastWorkout.text = args.lastWorkoutInfo
    }


    private fun navigateToWorkoutHistory() {

        // findNavController().popBackStack()

        findNavController().navigate(
            WorkoutFinishedFragmentDirections.actionWorkoutFinishedFragmentToWorkoutHistoryFragment()
        )
    }
}