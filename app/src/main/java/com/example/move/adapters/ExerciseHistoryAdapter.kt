package com.example.move.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.move.databinding.ExerciseHistoryItemBinding
import com.example.move.models.Block
import kotlin.math.roundToInt

class ExerciseHistoryAdapter :
    RecyclerView.Adapter<ExerciseHistoryAdapter.ExerciseHistoryViewHolder>() {

    inner class ExerciseHistoryViewHolder(binding: ExerciseHistoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val date = binding.tvDate
        val sets = binding.tvSets
        val oneRmValues = binding.tvOneRmValues
    }

    private val differCallback = object : DiffUtil.ItemCallback<Block>() {
        override fun areItemsTheSame(oldItem: Block, newItem: Block): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Block, newItem: Block): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ExerciseHistoryViewHolder {
        return ExerciseHistoryViewHolder(
            ExerciseHistoryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ExerciseHistoryViewHolder, position: Int) {
        val item = differ.currentList[position]

        holder.apply {
            date.textSize = 18F
            date.text = "Monday"
            sets.text =
                item.listOfSets.joinToString(separator = "\n") { "${it.weight} x ${it.reps}" }

            oneRmValues.text = item.listOfSets.joinToString(separator = "\n") {
                it.oneRepMax.roundToInt().toString()
            }
        }
    }

    override fun getItemCount(): Int = differ.currentList.size


}