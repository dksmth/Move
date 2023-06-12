package com.example.move.adapters

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.move.R
import com.example.move.models.OneSet

class SetAdapter (private val oneSet: List<OneSet>): RecyclerView.Adapter<SetAdapter.SetViewHolder>() {

    private var data = mutableListOf<String>()

    inner class SetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val setNumber: TextView = itemView.findViewById(R.id.tvNumberOfSets)
        var weight: EditText = itemView.findViewById(R.id.etWeight)
        val reps: EditText = itemView.findViewById(R.id.etReps)


        init {
            weight.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun afterTextChanged(p0: Editable?) {
                    addAddSetListener?.let { it(p0.toString() + ' ' +  adapterPosition) }
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

        holder.apply {
            weight.setText(set.weight.toString())
            reps.setText(set.reps.toString())
            setNumber.text = ((position + 1).toString())
        }

    }

    private var addAddSetListener: ((String) -> Unit)? = null

    fun smthSet(listener: (String) -> Unit) {
        addAddSetListener = listener
    }
}