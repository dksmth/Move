package com.example.move.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    tableName = "exercises"
)
data class ExerciseItem(
    @PrimaryKey(autoGenerate = true)
    var dbID: Int? = null,
    val bodyPart: String,
    val equipment: String,
    val gifUrl: String,
    val id: String,
    var name: String,
    val target: String
) : Parcelable {

    override fun equals(other: Any?): Boolean {
        return other is ExerciseItem
                && other.bodyPart == this.bodyPart
                && other.equipment == this.equipment
                && other.name == this.name
                && other.target == this.target
    }

    override fun hashCode(): Int {
        var result = bodyPart.hashCode()
        result = 31 * result + equipment.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + target.hashCode()
        return result
    }

}