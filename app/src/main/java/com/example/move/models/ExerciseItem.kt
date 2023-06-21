package com.example.move.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
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
}