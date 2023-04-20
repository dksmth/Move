package com.example.move.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.move.R
import com.example.move.adapters.ExercisesAdapter
import com.example.move.databinding.FragmentExercisesBinding
import com.example.move.models.ExerciseItem
import com.example.move.ui.ExercisesViewModel
import com.example.move.ui.MainActivity
import com.example.move.util.Resource


class ExerciseListFragment : Fragment() {

    private var _binding: FragmentExercisesBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: ExercisesViewModel

    private lateinit var exercisesAdapter: ExercisesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentExercisesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        setupRecyclerView()

        exercisesAdapter.setOnItemClickListener {
            navigate(it)
        }

        viewModel.exercises.observe(viewLifecycleOwner) { response ->
            handleResponse(response)
        }
    }

    private fun handleResponse(response: Resource<List<ExerciseItem>>) {
        when (response) {
            is Resource.Success -> {
                hideProgressBar()
                response.data?.let {
                    exercisesAdapter.differ.submitList(it)
                }
            }
            is Resource.Error -> {
                hideProgressBar()
                response.message?.let {
                    Log.d("TAG", "An error occurred $it")
                }
            }
            is Resource.Loading -> {
                showProgressBar()
            }
        }
    }

    private fun navigate(it: ExerciseItem) {
        val previousFragmentIsWorkout =
            findNavController().previousBackStackEntry?.destination?.id == R.id.workoutFragment

        findNavController().navigate(
            if (previousFragmentIsWorkout) {
                ExerciseListFragmentDirections
                    .actionExerciseListFragmentToWorkoutFragment(it)
            } else {
                ExerciseListFragmentDirections
                    .actionExerciseListFragmentToExerciseInfoFragment(it)
            }
        )
    }


    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        exercisesAdapter = ExercisesAdapter()
        binding.rvAllExercises.apply {
            adapter = exercisesAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}