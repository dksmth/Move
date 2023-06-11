package com.example.move.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.move.R
import com.example.move.adapters.BlockAdapter
import com.example.move.databinding.FragmentWorkoutBinding
import com.example.move.models.Block
import com.example.move.ui.viewmodels.WorkoutViewModel
import kotlinx.coroutines.launch

class WorkoutFragment : Fragment() {

    private val viewModel: WorkoutViewModel by lazy {
        ViewModelProvider(requireActivity())[WorkoutViewModel::class.java]
    }

    lateinit var blockAdapter: BlockAdapter

    private var _binding: FragmentWorkoutBinding? = null
    private val binding get() = _binding!!

    private val args: WorkoutFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkoutBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btAddExercise.setOnClickListener {
            findNavController().navigate(
                WorkoutFragmentDirections.actionWorkoutFragmentToPickExerciseFragment()
            )
        }

        setupBlockAdapter()

        viewModel.workout.observe(viewLifecycleOwner) { workout ->
            blockAdapter.differ.submitList(workout)
        }


        blockAdapter.setOnDeleteListener {
            viewModel.deleteExercise(it)
        }

        blockAdapter.setNavigateToExerciseListener {
            navigateToSetInfoFragment(it)
        }

        binding.btSaveWorkout.setOnClickListener {
            lifecycleScope.launch {
                viewModel.insertWorkout()
            }
        }
    }


    private fun navigateToSetInfoFragment(exercise: Block) {
        findNavController().navigate(
            WorkoutFragmentDirections.actionWorkoutFragmentToSetInfoFragment3(exercise)
        )
    }

    private fun setupBlockAdapter() {
        blockAdapter = BlockAdapter()
        binding.rvBlocks.apply {
            adapter = blockAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

}