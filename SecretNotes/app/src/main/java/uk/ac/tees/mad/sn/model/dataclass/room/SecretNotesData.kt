package uk.ac.tees.mad.sn.model.dataclass.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "secret_notes_data")
data class SecretNotesData(
    @PrimaryKey val id: String="",
    val userId: String="",
    val locked: Boolean=false,
    val title: String="",
    val summary: String="",
    val content: String="",
    val timestamp: Long= System.currentTimeMillis(),
)
