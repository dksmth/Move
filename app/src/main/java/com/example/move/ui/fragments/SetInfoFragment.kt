package com.example.move.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ScaleGestureDetector.OnScaleGestureListener
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.move.adapters.BlockAdapter
import com.example.move.adapters.SetsAdapter
import com.example.move.databinding.FragmentSetInfoBinding
import com.example.move.models.Block
import com.example.move.models.OneSet
import com.example.move.ui.viewmodels.WorkoutViewModel


class SetInfoFragment : Fragment() {

    private val viewModel: WorkoutViewModel by lazy {
        ViewModelProvider(requireActivity())[WorkoutViewModel::class.java]
    }

    lateinit var setAdapter: SetsAdapter

    private var _binding: FragmentSetInfoBinding? = null
    private val binding get() = _binding!!

    private val args: SetInfoFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val callback: OnBackPressedCallback =
//            object : OnBackPressedCallback(true) {
//                override fun handleOnBackPressed() {
//                    findNavController().navigate(
//                        SetInfoFragmentDirections.actionSetInfoFragment3ToWorkoutFragment()
//                    )
//                }
//            }
//
//        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSetInfoBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSetAdapter()

        args.block

        binding.button.setOnClickListener {
            viewModel.addSet(args.block)
        }

        viewModel.sets.observe(viewLifecycleOwner) {
            setAdapter.differ.submitList(it)
        }
    }

    private fun setupSetAdapter() {
        setAdapter = SetsAdapter(oneSet = listOf())
        binding.rvSet.apply {
            adapter = setAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }


}