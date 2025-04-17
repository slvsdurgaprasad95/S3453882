package uk.ac.tees.mad.sn.model.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uk.ac.tees.mad.sn.model.dataclass.room.SecretNotesData

@Dao
interface SecretNotesDataDao {
    /**
     * Inserts a new SecretNotesData into the database.
     *
     * @param secretNotesData The SecretNotesData to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(secretNotesData: SecretNotesData)

    /**
     * Inserts or updates a SecretNotesData based on matching id and userId.
     *
     * @param secretNotesData The SecretNotesData to be upserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(secretNotesData: SecretNotesData)

    /**
     * Retrieves all SecretNotesData associated with a specific userId.
     *
     * @param userId The ID of the user whose notes should be retrieved.
     * @return A list of SecretNotesData for the given userId.
     */
    @Query("SELECT * FROM secret_notes_data WHERE userId = :userId")
    suspend fun getAll(userId: String): List<SecretNotesData>

    /**
     * Retrieves a specific SecretNotesData by its id.
     *
     * @param id The ID of the SecretNotesData to be retrieved.
     * @return The SecretNotesData with the given ID, or null if no such note exists.
     */
    @Query("SELECT * FROM secret_notes_data WHERE id = :id")
    suspend fun getById(id: Int): SecretNotesData?

    /**
     * Retrieves all SecretNotesData where isSynced is false.
     *
     * @return A list of SecretNotesData where isSynced is false.
     */
    @Query("SELECT * FROM secret_notes_data WHERE isSynced = 0")
    suspend fun getAllWhereIsNotSynced(): List<SecretNotesData>

    /**
     * Updates specific fields (title, summary, content, timestamp, isSynced, isSecret, userProtectionEnabled) of a SecretNotesData.
     *
     * @param id The ID of the SecretNotesData to be updated.
     * @param title The new title for the SecretNotesData.
     * @param summary The new summary for the SecretNotesData.
     * @param content The new content for the SecretNotesData.
     * @param timestamp The new timestamp for the SecretNotesData.
     * @param isSynced The new isSynced status for the SecretNotesData.
     * @param isSecret The new isSecret status for the SecretNotesData.
     * @param userProtectionEnabled The new userProtectionEnabled status for the SecretNotesData.
     */
    @Query("UPDATE secret_notes_data SET title = :title, summary = :summary, content = :content, timestamp = :timestamp, isSynced = :isSynced, isSecret = :isSecret, userProtectionEnabled = :userProtectionEnabled WHERE id = :id")
    suspend fun update(
        id: Int,
        title: String,
        summary: String,
        content: String,
        timestamp: Long,
        isSynced: Boolean,
        isSecret: Boolean,
        userProtectionEnabled: Boolean
    )

    /**
     * Updates the timestamp of a specific SecretNotesData.
     *
     * @param id The ID of the SecretNotesData to be updated.
     * @param timestamp The new timestamp for the SecretNotesData.
     */
    @Query("UPDATE secret_notes_data SET timestamp = :timestamp WHERE id = :id")
    suspend fun updateTimestamp(id: Int, timestamp: Long)

    /**
     * Updates the isSynced status of a specific SecretNotesData.
     *
     * @param id The ID of the SecretNotesData to be updated.
     * @param isSynced The new isSynced status for the SecretNotesData.
     */
    @Query("UPDATE secret_notes_data SET isSynced = :isSynced WHERE id = :id")
    suspend fun updateIsSynced(id: Int, isSynced: Boolean)

    /**
     * Updates the isSecret status of a specific SecretNotesData.
     *
     * @param id The ID of the SecretNotesData to be updated.
     * @param isSecret The new isSecret status for the SecretNotesData.
     */
    @Query("UPDATE secret_notes_data SET isSecret = :isSecret WHERE id = :id")
    suspend fun updateIsSecret(id: Int, isSecret: Boolean)

    /**
     * Updates the userProtectionEnabled status of a specific SecretNotesData.
     *
     * @param userId The user Id of the SecretNotesData to be updated.
     * @param userProtectionEnabled The new userProtectionEnabled status for the SecretNotesData.
     */
    @Query("UPDATE secret_notes_data SET userProtectionEnabled = :userProtectionEnabled WHERE userId = :userId")
    suspend fun updateUserProtectionEnabled(userId: String, userProtectionEnabled: Boolean)

    /**
     * Deletes all SecretNotesData associated with a specific userId.
     *
     * @param userId The ID of the user whose notes should be deleted.
     */
    @Query("DELETE FROM secret_notes_data WHERE userId = :userId")
    suspend fun deleteAll(userId: String)

    /**
     * Deletes a specific SecretNotesData by its id.
     *
     * @param id The ID of the SecretNotesData to be deleted.
     */
    @Query("DELETE FROM secret_notes_data WHERE id = :id")
    suspend fun deleteById(id: Int)
}