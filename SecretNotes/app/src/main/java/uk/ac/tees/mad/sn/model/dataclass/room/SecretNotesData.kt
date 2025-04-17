package uk.ac.tees.mad.sn.model.dataclass.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "secret_notes_data")
data class SecretNotesData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: String,
    val isSecret: Boolean,
    val title: String,
    val summary: String,
    val content: String,
    val timestamp: Long,
    val isSynced: Boolean,
    val userProtectionEnabled: Boolean = true
)
