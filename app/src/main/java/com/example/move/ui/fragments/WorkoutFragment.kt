package com.example.move.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.move.adapters.BlockAdapter
import com.example.move.adapters.SetAdapter
import com.example.move.databinding.FragmentWorkoutBinding
import com.example.move.models.Block
import com.example.move.models.OneSet
import com.example.move.ui.ExercisesViewModel
import com.example.move.ui.MainActivity

class WorkoutFragment : Fragment() {

    lateinit var viewModel: ExercisesViewModel

    lateinit var blockAdapter: BlockAdapter

    private var _binding: FragmentWorkoutBinding? = null
    private val binding get() = _binding!!

    private val args: WorkoutFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkoutBinding.inflate(inflater, container, false)

        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        binding.btAddExercise.setOnClickListener {
            findNavController().navigate(
                WorkoutFragmentDirections.actionWorkoutFragmentToExerciseListFragment()
            )
        }


        if (args.chosenExercise != null) {
            viewModel.addExercise(Block(args.chosenExercise!!))
        }


        setupBlockAdapter(block = Block())

        viewModel.workout.observe(viewLifecycleOwner) { workout ->
            val list = mutableListOf<Block>()

            workout.forEach {
                list += it
            }

            blockAdapter.differ.submitList(list)
        }

        blockAdapter.setOnAddSetListener {
            viewModel.addSet(it)
        }
    }


    private fun setupBlockAdapter(block: Block) {
        blockAdapter = BlockAdapter(listOf(block))
        binding.rvBlocks.apply {
            adapter = blockAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

}