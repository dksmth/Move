package com.example.move.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.move.R
import com.example.move.models.Block

class BlockAdapter(): RecyclerView.Adapter<BlockAdapter.BlockViewHolder>() {


    inner class BlockViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val rvExercise: RecyclerView = itemView.findViewById(R.id.rvExercise)
        val btDelete: Button = itemView.findViewById(R.id.btDeleteButton)
    }

    private val differCallback = object : DiffUtil.ItemCallback<Block>() {
        override fun areItemsTheSame(oldItem: Block, newItem: Block): Boolean {
            return oldItem.exercise == newItem.exercise && oldItem.listOfSets == newItem.listOfSets
        }

        override fun areContentsTheSame(oldItem: Block, newItem: Block): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlockAdapter.BlockViewHolder {
        return BlockViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_exercise_block,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: BlockAdapter.BlockViewHolder, position: Int) {
        val block = differ.currentList[position]

        holder.apply {
            tvName.text = block.exercise?.name
        }

        holder.apply {
            val setAdapter = SetAdapter(block.listOfSets.toList())
            setAdapter.differ.submitList(block.listOfSets)
            rvExercise.adapter = setAdapter
        }


        holder.btDelete.setOnClickListener {
            addDeleteBlockListener?.let { it(block) }
        }

        holder.itemView.setOnClickListener {
            addNavigateToExerciseListener?.let { it(block) }
        }
    }

    private var addNavigateToExerciseListener: ((Block) -> Unit)? = null

    fun setNavigateToExerciseListener(listener: (Block) -> Unit) {
        addNavigateToExerciseListener = listener
    }

    private var addDeleteBlockListener: ((Block) -> Unit)? = null

    fun setOnDeleteListener(listener: (Block) -> Unit) {
        addDeleteBlockListener = listener
    }

}