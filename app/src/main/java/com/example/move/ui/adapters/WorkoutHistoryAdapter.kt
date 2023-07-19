package com.example.move.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.move.databinding.WorkoutHistoryRvItemBinding
import com.example.move.models.BlocksWithDateTime


class WorkoutHistoryAdapter :
    RecyclerView.Adapter<WorkoutHistoryAdapter.ViewHolder>() {

    inner class ViewHolder(binding: WorkoutHistoryRvItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val body = binding.workoutBody
        val workoutName = binding.tvDate
        val bestSet = binding.bestSetsColumn
    }

    private val differCallback = object : DiffUtil.ItemCallback<BlocksWithDateTime>() {
        override fun areItemsTheSame(oldItem: BlocksWithDateTime, newItem: BlocksWithDateTime): Boolean {
            return oldItem.dateTime == newItem.dateTime && oldItem.blocks == newItem.blocks
        }

        override fun areContentsTheSame(oldItem: BlocksWithDateTime, newItem: BlocksWithDateTime): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            WorkoutHistoryRvItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = differ.currentList[position]

        holder.apply {
            var setsAndName = ""
            var bestSetString = ""

            item.blocks.forEach { block ->
                val bestSet = block.listOfSets.maxBy { it.oneRepMax }

                bestSetString += "${bestSet.weight} x ${bestSet.reps}" + "\n"
                setsAndName += "${block.listOfSets.size} x ${block.exercise.name} \n"
            }

            body.text = setsAndName.trim()
            bestSet.text = bestSetString.trim()
            workoutName.text = item.dateTime
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

}