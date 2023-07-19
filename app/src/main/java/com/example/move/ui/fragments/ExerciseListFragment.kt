package com.example.move.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.move.ui.adapters.ExercisesAdapter
import com.example.move.databinding.FragmentExercisesBinding
import com.example.move.models.ExerciseItem
import com.example.move.ui.viewmodels.ExercisesViewModel
import com.example.move.ui.viewmodels.WorkoutViewModel
import com.example.move.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ExerciseListFragment : Fragment() {

    private var _binding: FragmentExercisesBinding? = null
    private val binding get() = _binding!!

    private val exercisesViewModel: ExercisesViewModel by viewModels()

    private val workoutViewModel: WorkoutViewModel by activityViewModels()


    private lateinit var exercisesAdapter: ExercisesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentExercisesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        exercisesAdapter.setOnItemClickListener { exercise ->
            if (workoutViewModel.openedForResult) {

                if (workoutViewModel.isInWorkout(exercise)) {
                    showToastForDuplicateExercise()
                } else {
                    workoutViewModel.addExercise(exercise)
                    findNavController().popBackStack()
                }
            } else {
                navigateToExerciseInfo(exercise)
            }
        }

        exercisesViewModel.exercises.observe(viewLifecycleOwner) { response ->
            handleResponse(response)
        }

        binding.searchView.setOnQueryTextListener(
            DebouncingQueryTextListener(lifecycle) { newText ->
                newText?.let {
                    updateList(it)
                }
            }
        )
    }

    internal class DebouncingQueryTextListener(
        lifecycle: Lifecycle,
        private val onDebouncingQueryTextChange: (String?) -> Unit,
    ) : SearchView.OnQueryTextListener {

        var debouncePeriod: Long = 300
        private val coroutineScope = lifecycle.coroutineScope
        private var searchJob: Job? = null

        override fun onQueryTextSubmit(query: String?): Boolean {
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            searchJob?.cancel()
            searchJob = coroutineScope.launch {
                newText?.let {
                    delay(debouncePeriod)
                    onDebouncingQueryTextChange(newText)
                }
            }
            return false
        }
    }

    private fun updateList(str: String) {
        val filteredList = exercisesViewModel.filter(str)

        exercisesAdapter.differ.submitList(exercisesViewModel.filter(str))

        if (filteredList.isEmpty()) {
            Toast.makeText(requireActivity(), "Nothing found", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showToastForDuplicateExercise() {
        Toast.makeText(requireActivity(), "Cant add another", Toast.LENGTH_SHORT).show()
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

    private fun navigateToExerciseInfo(exercise: ExerciseItem) {
        clearSearchView()

        findNavController().navigate(
            ExerciseListFragmentDirections.actionExerciseListFragmentToExerciseInfoFragment(exercise)
        )
    }

    private fun clearSearchView() {
        binding.searchView.setQuery("", false)
        binding.searchView.clearFocus()
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