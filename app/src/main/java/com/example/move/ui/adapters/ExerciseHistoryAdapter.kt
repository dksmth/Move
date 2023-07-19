package com.example.move.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.move.databinding.ExerciseInfoRvItemBinding
import com.example.move.models.Block
import com.example.move.util.parseWeight
import kotlin.math.roundToInt

class ExerciseHistoryAdapter :
    RecyclerView.Adapter<ExerciseHistoryAdapter.ExerciseHistoryViewHolder>() {

    inner class ExerciseHistoryViewHolder(binding: ExerciseInfoRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val date = binding.tvDate
        private val sets = binding.tvSets
        private val oneRmValues = binding.tvOneRmValues

        fun bind(block: Block) {
            date.text = block.dateTime

            sets.text =
                block.listOfSets.joinToString("\n") { "${it.weight.parseWeight()} kg x ${it.reps}" }

            oneRmValues.text =
                block.listOfSets.joinToString("\n") { it.oneRepMax.roundToInt().toString() }
        }
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
            ExerciseInfoRvItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ExerciseHistoryViewHolder, position: Int) {
        val sets = differ.currentList[position]

        holder.bind(sets)
    }

    override fun getItemCount(): Int = differ.currentList.size
}