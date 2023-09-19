package com.example.move.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.move.ui.adapters.BlockAdapter
import com.example.move.databinding.FragmentWorkoutBinding
import com.example.move.models.Block
import com.example.move.ui.viewmodels.WorkoutViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WorkoutFragment : Fragment() {

    private val workoutViewModel: WorkoutViewModel by activityViewModels()


    private var _binding: FragmentWorkoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentWorkoutBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val blockAdapter = BlockAdapter(
            addSetListener = { workoutViewModel.addSet(it) },
            deleteSetListener = { block, i -> workoutViewModel.deleteSet(block, i) },
            deleteExerciseListener = { workoutViewModel.deleteExercise(it) },

            changeSetListener = { block: Block, strings: List<String> ->
                workoutViewModel.changeSet(block, strings)
            }
        )

        setupBlockAdapter(blockAdapter)

        workoutViewModel._workout.observe(viewLifecycleOwner) { workout ->
            blockAdapter.differ.submitList(workout)
        }

        binding.btAddExercise.setOnClickListener {
            workoutViewModel.setFlagForOpeningWithResult(true)
            navigateToExerciseList()
        }

        binding.btSaveWorkout.setOnClickListener {
            endWorkout()
        }
    }

    private fun endWorkout() {
        if (workoutViewModel.canBeFinished()) {
            lifecycleScope.launch {
                workoutViewModel.insertWorkout()
            }

            val workoutInfo = workoutViewModel.getWorkoutInfo()

            workoutViewModel.endWorkout()
            navigateToEndScreen(workoutInfo)
        } else {
            Toast.makeText(
                requireContext(),
                "Cant end workout - unfinished sets",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun navigateToExerciseList() {
        findNavController().navigate(
            WorkoutFragmentDirections.actionWorkoutFragmentToExerciseListFragment()
        )
    }

    private fun navigateToEndScreen(workoutInfo: String) {
        findNavController().navigate(
            WorkoutFragmentDirections.actionWorkoutFragmentToWorkoutFinishedFragment(workoutInfo)
        )
    }

    private fun setupBlockAdapter(blockAdapter: BlockAdapter) {
        binding.rvBlocks.apply {
            adapter = blockAdapter
            layoutManager = LinearLayoutManager(activity)

            itemAnimator = null
        }
    }

}