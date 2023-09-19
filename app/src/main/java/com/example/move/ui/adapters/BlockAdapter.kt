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
import com.example.move.databinding.ItemExerciseBlockBinding
import com.example.move.models.Block

class BlockAdapter(
    var deleteExerciseListener: ((Block) -> Unit),
    var addSetListener: ((Block) -> Unit),
    var changeSetListener: ((Block, List<String>) -> Unit),
    var deleteSetListener: ((Block, Int) -> Unit),
) : RecyclerView.Adapter<BlockAdapter.BlockViewHolder>() {

    class BlockViewHolder(binding: ItemExerciseBlockBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvName: TextView = binding.tvName
        val rvExercise: RecyclerView = binding.rvExercise
        val btDelete: ImageButton = binding.btDeleteButton
        val btAddSet: Button = binding.btAddSet
        val setAdapter = SetAdapter()
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
            ItemExerciseBlockBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BlockViewHolder, position: Int) {
        val block = differ.currentList[position]

        holder.setAdapter.differ.submitList(block.listOfSets)

        holder.apply {
            rvExercise.adapter = setAdapter
            tvName.text = block.exercise.name

            btDelete.setOnClickListener {
                deleteExerciseListener.invoke(block)
            }

            btAddSet.setOnClickListener {
                addSetListener.invoke(block)
                notifyItemChanged(differ.currentList.indexOf(block))
            }

            setAdapter.changeSetListener = { setInfo ->
                changeSetListener.invoke(block, setInfo)
            }

            setAdapter.deleteSetListener = { position ->
                deleteSetListener.invoke(block, position)
                notifyItemChanged(differ.currentList.indexOf(block))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}