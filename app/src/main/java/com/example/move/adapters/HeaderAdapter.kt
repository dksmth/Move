package com.example.move.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.move.databinding.HeaderWithAppBarBinding
import com.example.move.models.ExerciseItem

class HeaderAdapter(val exercise: ExerciseItem): RecyclerView.Adapter<HeaderAdapter.HeaderViewHolder>() {

    var navigateBack: () -> Boolean? = { false }

    inner class HeaderViewHolder(binding: HeaderWithAppBarBinding) : RecyclerView.ViewHolder(binding.root) {
        private val bodypart = binding.includedHeader.tvBodypartHere
        private val muscle = binding.includedHeader.tvMuscleHere
        private val equipment = binding.includedHeader.tvEquipmentHere
        private val gif = binding.includedHeader.ivExerciseGif
        val appBar = binding.topAppBar

        fun bind() {
            bodypart.text = exercise.bodyPart
            muscle.text = exercise.target
            equipment.text = exercise.equipment
            appBar.title = exercise.name

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
            HeaderWithAppBarBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        )
    }

    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) {
        holder.bind()

        holder.appBar.setNavigationOnClickListener {
            navigateBack.invoke()
        }
    }

    override fun getItemCount(): Int = 1
}