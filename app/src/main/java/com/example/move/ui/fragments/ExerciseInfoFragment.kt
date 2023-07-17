package com.example.move.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.move.adapters.ExerciseHistoryAdapter
import com.example.move.adapters.HeaderAdapter
import com.example.move.databinding.ExerciseInfoRecyclerviewBinding
import com.example.move.models.ExerciseItem
import com.example.move.ui.MainActivity
import com.example.move.ui.viewmodels.WorkoutHistoryViewModel


class ExerciseInfoFragment : Fragment() {

    private var _binding: ExerciseInfoRecyclerviewBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: WorkoutHistoryViewModel

    private val args: ExerciseInfoFragmentArgs by navArgs()

    lateinit var exerciseHistoryAdapter: ExerciseHistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = ExerciseInfoRecyclerviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).workoutHistoryViewModel

        val exercise = args.exercise

        setupAdapter(exercise)

        viewModel.getExerciseHistory(exercise)

        viewModel.historyOfExercise.observe(viewLifecycleOwner) { listOfExercise ->
            exerciseHistoryAdapter.differ.submitList(listOfExercise)
        }
    }

    private fun setupAdapter(exercise: ExerciseItem) {
        exerciseHistoryAdapter = ExerciseHistoryAdapter()

        binding.root.apply {
            adapter = ConcatAdapter(HeaderAdapter(exercise).apply {
                navigateBack = {
                    navigateBack()
                }
            }, exerciseHistoryAdapter)
            layoutManager = LinearLayoutManager(activity)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigateBack() {
        findNavController().popBackStack()
    }

}