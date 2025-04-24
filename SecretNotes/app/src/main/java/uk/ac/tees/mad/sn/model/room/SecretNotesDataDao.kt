package uk.ac.tees.mad.sn.model.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import uk.ac.tees.mad.sn.model.dataclass.room.SecretNotesData

@Dao
interface SecretNotesDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(entity: SecretNotesData)

    @Query("SELECT * FROM secret_notes_data WHERE id =:id LIMIT 1")
    fun getNoteById(id: String): Flow<SecretNotesData?>

    @Query("SELECT * FROM secret_notes_data WHERE userID =:userId ORDER BY timestamp DESC")
    fun getAllNotes(userId: String):Flow<List<SecretNotesData>>

    @Query("DELETE FROM secret_notes_data WHERE id =:id")
    suspend fun deleteNote(id: String)
}