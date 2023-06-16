package com.example.move.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.move.databinding.FragmentItemBinding
import com.example.move.models.Workout


class MyWorkoutHistoryRecyclerViewAdapter() : RecyclerView.Adapter<MyWorkoutHistoryRecyclerViewAdapter.ViewHolder>() {


    inner class ViewHolder(binding: FragmentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val body = binding.workoutBody
        val workoutName = binding.workoutName
        val dateTime = binding.dateTime
    }

    private val differCallback = object : DiffUtil.ItemCallback<Workout>() {
        override fun areItemsTheSame(oldItem: Workout, newItem: Workout): Boolean {
            return oldItem.dbID == newItem.dbID
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
            body.text = item.blocks.toString()
            workoutName.text = item.blocks?.size.toString()
        }
    }

    override fun getItemCount(): Int = differ.currentList.size



}