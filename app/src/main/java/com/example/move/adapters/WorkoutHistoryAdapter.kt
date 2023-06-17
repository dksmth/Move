package com.example.move.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.move.databinding.FragmentItemBinding
import com.example.move.models.OneSet
import com.example.move.models.Workout
import com.example.move.util.roundToDecimal
import com.example.move.util.trimLastIf


class WorkoutHistoryAdapter() :
    RecyclerView.Adapter<WorkoutHistoryAdapter.ViewHolder>() {


    inner class ViewHolder(binding: FragmentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val body = binding.workoutBody
        val workoutName = binding.workoutName
        val dateTime = binding.dateTime
    }

    private val differCallback = object : DiffUtil.ItemCallback<Workout>() {
        override fun areItemsTheSame(oldItem: Workout, newItem: Workout): Boolean {
            return oldItem.workout_id == newItem.workout_id
        }

        override fun areContentsTheSame(oldItem: Workout, newItem: Workout): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = differ.currentList[position]

        holder.apply {
            var string = ""

            item.blocks?.forEach { block ->

                val sets = setsToString(block.listOfSets)

                string += block.exercise?.name + "\n" + sets + "\n"
            }

            body.text = string
            workoutName.text = item.dateTime
        }
    }

    private fun setsToString(blocks: List<OneSet>): String {
        return blocks.joinToString(
            separator = "\n",
            transform = { set ->
                "${set.weight} x ${set.reps}  ${
                    set.oneRepMax.roundToDecimal(1).trimLastIf(",0")
                }"
            })
    }

    override fun getItemCount(): Int = differ.currentList.size

}