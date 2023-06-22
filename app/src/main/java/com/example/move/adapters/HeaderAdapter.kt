package com.example.move.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.move.databinding.ExerciseInfoHeaderBinding
import com.example.move.models.ExerciseItem

class HeaderAdapter(val exercise: ExerciseItem): RecyclerView.Adapter<HeaderAdapter.HeaderViewHolder>() {

    inner class HeaderViewHolder(binding: ExerciseInfoHeaderBinding) : RecyclerView.ViewHolder(binding.root) {
        private val bodypart = binding.tvBodypartHere
        private val muscle = binding.tvMuscleHere
        private val equipment = binding.tvEquipmentHere
        private val gif = binding.ivExerciseGif

        fun bind() {
            bodypart.text = exercise.bodyPart
            muscle.text = exercise.target
            equipment.text = exercise.equipment

            Glide.with(gif)
                .asGif()
                .centerCrop()
                .load(exercise.gifUrl)
                .into(gif)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): HeaderViewHolder {
        return HeaderViewHolder(
            ExerciseInfoHeaderBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        )
    }

    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = 1
}