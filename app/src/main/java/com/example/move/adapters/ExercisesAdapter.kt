package com.example.move.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.move.R
import com.example.move.models.ExerciseItem

class ExercisesAdapter: RecyclerView.Adapter<ExercisesAdapter.ExerciseViewHolder>() {

    inner class ExerciseViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val ivGif: ImageView = itemView.findViewById(R.id.ivExerciseGif)
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvMuscleGroup: TextView = itemView.findViewById(R.id.tvMuscleGroup)
    }

    private val differCallback = object : DiffUtil.ItemCallback<ExerciseItem>() {
        override fun areItemsTheSame(oldItem: ExerciseItem, newItem: ExerciseItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ExerciseItem, newItem: ExerciseItem): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        return ExerciseViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_exercise,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = differ.currentList[position]

        holder.apply {

            loadImageInView(exercise, holder)

            tvName.text = exercise.name
            tvMuscleGroup.text = exercise.bodyPart
        }

        holder.itemView.setOnClickListener {
                onItemClickListener?.let { it(exercise) }
            }
        }

    fun filterList(filteredExercises: List<ExerciseItem>) {
        differ.submitList(filteredExercises)
    }

    private fun ExerciseViewHolder.loadImageInView(
        exercise: ExerciseItem,
        holder: ExerciseViewHolder
    ) {
        Glide.with(holder.itemView)
            .asBitmap()
            .timeout(6000)
            .load(exercise.gifUrl)
            .into(ivGif)
    }

    private var onItemClickListener: ((ExerciseItem) -> Unit)? = null

    fun setOnItemClickListener(listener: (ExerciseItem) -> Unit) {
        onItemClickListener = listener
    }
}