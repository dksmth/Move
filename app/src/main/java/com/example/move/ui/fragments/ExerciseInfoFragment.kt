package com.example.move.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.move.databinding.FragmentExerciseInfoBinding
import com.example.move.ui.ExercisesViewModel
import com.example.move.ui.MainActivity


class ExerciseInfoFragment : Fragment() {

    private var _binding: FragmentExerciseInfoBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: ExercisesViewModel

    private val args: ExerciseInfoFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentExerciseInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        val exercise = args.exercise

        binding.apply {
            val gifUrlHttpsWrapped = "https" + exercise.gifUrl.removeRange(IntRange(0,3))

            Glide.with(requireActivity())
                .asGif()
                .centerCrop()
                .load(gifUrlHttpsWrapped)
                .into(ivExerciseGif)

            tvBodypartEdit.text = exercise.bodyPart
            tvTargetMuscleEdit.text = exercise.target
            tvEquipmentEdit.text = exercise.equipment
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}