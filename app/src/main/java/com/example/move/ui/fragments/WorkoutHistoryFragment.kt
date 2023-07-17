package com.example.move.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.move.adapters.WorkoutHistoryAdapter
import com.example.move.databinding.FragmentItemListBinding
import com.example.move.ui.MainActivity
import com.example.move.ui.viewmodels.WorkoutHistoryViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class WorkoutHistoryFragment : Fragment() {

    private var _binding: FragmentItemListBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: WorkoutHistoryViewModel

    private lateinit var workoutsAdapter: WorkoutHistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentItemListBinding.inflate(inflater, container, false)

        setupBlockAdapter()

        return binding.root
    }

    private fun setupBlockAdapter() {
        workoutsAdapter = WorkoutHistoryAdapter()

        binding.root.apply {
            adapter = workoutsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).workoutHistoryViewModel

        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.getAllWorkouts()
        }

        viewModel.mapWorkoutToBlocks.observe(viewLifecycleOwner) { map ->
            workoutsAdapter.differ.submitList(map)
        }
    }
}