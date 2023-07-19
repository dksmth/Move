package com.example.move.ui.adapters

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.move.R
import com.example.move.models.OneSet
import com.example.move.util.trimLastIf

class SetAdapter : RecyclerView.Adapter<SetAdapter.SetViewHolder>() {

    var changeSetListener: ((List<String>) -> Unit)? = null

    var deleteSetListener: ((Int) -> Unit)? = null

    inner class SetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val setNumber: TextView = itemView.findViewById(R.id.tvNumberOfSets)
        var weight: EditText = itemView.findViewById(R.id.etWeight)
        val reps: EditText = itemView.findViewById(R.id.etReps)
        val btDelete: ImageButton = itemView.findViewById(R.id.btDeleteSet)

        fun addListener(inputField: EditText, isReps: Boolean) {
            inputField.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    val typeName = if (isReps) "reps" else "weight"

                    val textPositionValue =
                        listOf(
                            p0.toString(),
                            this@SetViewHolder.adapterPosition.toString(),
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
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_sets,
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
        val positionForPublic = holder.adapterPosition + 1

        holder.apply {

            if (set.weight != 0.0) weight.setText(set.weight.toString().trimLastIf(".0"))
            if (set.reps != 0) reps.setText(set.reps.toString())

            setNumber.text = positionForPublic.toString()

            btDelete.setOnClickListener {
                deleteSetListener?.invoke(position)
            }

            addListener(weight, isReps = false)
            addListener(reps, isReps = true)
        }

    }


}