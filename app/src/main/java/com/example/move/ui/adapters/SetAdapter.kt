package com.example.move.ui.adapters

import android.text.Editable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.move.databinding.ItemSetsBinding
import com.example.move.models.OneSet
import com.example.move.util.SimpleTextWatcher
import com.example.move.util.trimLastIf

class SetAdapter : RecyclerView.Adapter<SetAdapter.SetViewHolder>() {

    var changeSetListener: ((List<String>) -> Unit)? = null

    var deleteSetListener: ((Int) -> Unit)? = null

    inner class SetViewHolder(binding: ItemSetsBinding) : RecyclerView.ViewHolder(binding.root) {
        val setNumber: TextView = binding.tvNumberOfSets
        var weight: EditText = binding.etWeight
        val reps: EditText = binding.etReps
        val btDelete: ImageButton = binding.btDeleteSet

        fun addListener(inputField: EditText, typeName: String) {
            inputField.addTextChangedListener(object : SimpleTextWatcher() {
                override fun afterTextChanged(s: Editable) {

                    val textPositionValue =
                        listOf(
                            s.toString(),
                            this@SetViewHolder.layoutPosition.toString(),
                            typeName
                        )

                    changeSetListener?.let { it(textPositionValue) }
                }
            })
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<OneSet>() {
        override fun areItemsTheSame(oldItem: OneSet, newItem: OneSet): Boolean {
            return oldItem.weight == newItem.weight && oldItem.reps == newItem.reps
        }

        override fun areContentsTheSame(oldItem: OneSet, newItem: OneSet): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetViewHolder {
        return SetViewHolder(
            ItemSetsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: SetViewHolder, position: Int) {
        val set = differ.currentList[position]
        val positionForPublic = holder.bindingAdapterPosition + 1

        holder.apply {

            if (set.weight != 0.0) weight.setText(set.weight.toString().trimLastIf(".0"))
            if (set.reps != 0) reps.setText(set.reps.toString())

            setNumber.text = positionForPublic.toString()

            btDelete.setOnClickListener {
                deleteSetListener?.invoke(position)
            }

            addListener(weight, "weight")
            addListener(reps, "reps")
        }

    }

}

