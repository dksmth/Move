package com.example.move.ui.fragments

import android.os.Bundle
import android.text.Layout.Directions
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.move.adapters.ExercisesAdapter
import com.example.move.databinding.FragmentExercisesBinding
import com.example.move.ui.ExercisesViewModel
import com.example.move.ui.MainActivity
import com.example.move.util.Resource


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ExerciseListFragment : Fragment() {

    private var _binding: FragmentExercisesBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: ExercisesViewModel

    lateinit var exercisesAdapter: ExercisesAdapter

    val TAG = "ExerciseListFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentExercisesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        setupRecyclerView()

        exercisesAdapter.setOnItemClickListener {
            findNavController().navigate(
                ExerciseListFragmentDirections
                    .actionExerciseListFragmentToExerciseInfoFragment(it)
            )
        }

        viewModel.exercises.observe(viewLifecycleOwner) { response ->
            when(response) {
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