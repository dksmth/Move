package com.example.move.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.move.databinding.FragmentWorkoutHistoryBinding
import com.example.move.ui.adapters.WorkoutHistoryAdapter
import com.example.move.ui.viewmodels.WorkoutHistoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WorkoutHistoryFragment : Fragment() {

    private var _binding: FragmentWorkoutHistoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WorkoutHistoryViewModel by viewModels()

    private val workoutsAdapter = WorkoutHistoryAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentWorkoutHistoryBinding.inflate(inflater, container, false)

        setupBlockAdapter()
        viewModel.getAllWorkouts()

        return binding.root
    }

    private fun setupBlockAdapter() {
        binding.root.apply {
            adapter = workoutsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.mapWorkoutToBlocks.observe(viewLifecycleOwner) { map ->
            workoutsAdapter.differ.submitList(map)
        }
    }
}