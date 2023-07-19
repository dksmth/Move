package com.example.move.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.move.R
import com.example.move.models.Block

class BlockAdapter : RecyclerView.Adapter<BlockAdapter.BlockViewHolder>() {

    var deleteExerciseListener: ((Block) -> Unit)? = null

    var addSetListener: ((Block) -> Unit)? = null

    var changeSetListener: ((Block, List<String>) -> Unit)? = null

    var deleteSetListener: ((Block, Int) -> Unit)? = null

    inner class BlockViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val rvExercise: RecyclerView = itemView.findViewById(R.id.rvExercise)
        val btDelete: ImageButton = itemView.findViewById(R.id.btDeleteButton)
        val btAddSet: Button = itemView.findViewById(R.id.btAddSet)
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

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BlockViewHolder {
        return BlockViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_exercise_block,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BlockViewHolder, position: Int) {
        val block = differ.currentList[position]

        val setAdapter = SetAdapter().apply {
            differ.submitList(block.listOfSets)
        }

        holder.apply {
            rvExercise.adapter = setAdapter
            tvName.text = block.exercise.name

            btDelete.setOnClickListener {
                deleteExerciseListener?.invoke(block)
            }

            btAddSet.setOnClickListener {
                addSetListener?.invoke(block)
            }

            setAdapter.changeSetListener = { setInfo ->
                changeSetListener?.invoke(block, setInfo)
            }

            setAdapter.deleteSetListener = { position ->
                deleteSetListener?.invoke(block, position)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}