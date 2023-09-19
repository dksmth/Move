package com.example.move.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.move.ui.adapters.ExerciseHistoryAdapter
import com.example.move.databinding.FragmentExerciseInfoBinding
import com.example.move.models.ExerciseItem
import com.example.move.ui.viewmodels.WorkoutHistoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExerciseInfoFragment : Fragment() {

    private var _binding: FragmentExerciseInfoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WorkoutHistoryViewModel by viewModels()

    private val args: ExerciseInfoFragmentArgs by navArgs()

    private val exerciseHistoryAdapter: ExerciseHistoryAdapter = ExerciseHistoryAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentExerciseInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val exercise = args.exercise

        viewModel.getExerciseHistory(exercise)

        setupViews(exercise)
        setupAdapter()

        viewModel.historyOfExercise.observe(viewLifecycleOwner) { listOfExercise ->
            exerciseHistoryAdapter.differ.submitList(listOfExercise)
        }
    }

    private fun setupViews(exercise: ExerciseItem) {
        binding.apply {
            tvBodypart.text = exercise.bodyPart
            tvMuscleHere.text = exercise.target
            tvEquipmentHere.text = exercise.equipment
            topAppBar.title = exercise.name

            topAppBar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }

            Glide.with(binding.ivExerciseGif)
                .asGif()
                .centerCrop()
                .load(exercise.gifUrl)
                .into(binding.ivExerciseGif)
        }
    }

    private fun setupAdapter() {
        binding.rvExerciseHistory.apply {
            adapter = exerciseHistoryAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}