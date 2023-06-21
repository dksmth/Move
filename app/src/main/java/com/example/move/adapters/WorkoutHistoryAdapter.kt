package com.example.move.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.move.databinding.CardsForWorkoutHistoryBinding
import com.example.move.models.OneSet
import com.example.move.models.Workout
import com.example.move.util.roundToDecimal
import com.example.move.util.trimLastIf


class WorkoutHistoryAdapter() :
    RecyclerView.Adapter<WorkoutHistoryAdapter.ViewHolder>() {


    inner class ViewHolder(binding: CardsForWorkoutHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        val body = binding.workoutBody
        val workoutName = binding.tvDate
        val bestSet = binding.bestSetsColumn
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
            CardsForWorkoutHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = differ.currentList[position]

        holder.apply {
            var setsAndName = "\n"
            var bestSetString = "\n"

            item.blocks?.forEach { block ->
                val bestSet = block.listOfSets.maxBy { it.oneRepMax }

                bestSetString += "${bestSet.weight} x ${bestSet.reps}" + "\n"

                setsAndName += block.listOfSets.size.toString() + " x " + block.exercise?.name + "\n"
            }

            body.text = setsAndName
            bestSet.text = bestSetString
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