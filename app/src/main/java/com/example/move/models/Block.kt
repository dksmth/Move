package com.example.move.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    tableName = "blocks",

)
data class Block(
    @PrimaryKey(autoGenerate = true)
    val block_id: Int? = null,
    var exercise: ExerciseItem? = null,
    var listOfSets: MutableList<OneSet> = mutableListOf(OneSet(0.0, 0)),
    val comment: String? = null,
    var workout_id: Int? = null
) : Parcelable {
    override fun toString(): String {
        return this.exercise?.name.toString() + " :" + listOfSets.joinToString(separator = ",")
    }
}