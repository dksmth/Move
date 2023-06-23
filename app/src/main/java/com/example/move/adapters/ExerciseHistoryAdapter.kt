package com.example.move.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.move.databinding.ExerciseHistoryItemBinding
import com.example.move.models.Block
import com.example.move.util.parseWeight
import com.example.move.util.roundToDecimal
import com.example.move.util.trimLastIf
import kotlin.math.roundToInt

class ExerciseHistoryAdapter :
    RecyclerView.Adapter<ExerciseHistoryAdapter.ExerciseHistoryViewHolder>() {

    lateinit var dateTime: List<String>

    inner class ExerciseHistoryViewHolder(binding: ExerciseHistoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val date = binding.tvDate
        private val sets = binding.tvSets
        private val oneRmValues = binding.tvOneRmValues

        fun bind(block: Block, position: Int) {
            if (dateTime.isNotEmpty()) date.text = dateTime[position]

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
            ExerciseHistoryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ExerciseHistoryViewHolder, position: Int) {
        holder.bind(differ.currentList[position], position)
    }

    override fun getItemCount(): Int = differ.currentList.size
}