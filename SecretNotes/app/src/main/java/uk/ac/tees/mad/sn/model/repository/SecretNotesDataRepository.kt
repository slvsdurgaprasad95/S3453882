package uk.ac.tees.mad.sn.model.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uk.ac.tees.mad.sn.model.dataclass.room.SecretNotesData
import uk.ac.tees.mad.sn.model.room.SecretNotesDataDao

/**
 * Repository class that handles data operations for SecretNotesData.
 *
 * This repository abstracts the data access from the underlying data source (Room database)
 * and provides a clean API for interacting with SecretNotesData.
 */
class SecretNotesDataRepository(private val secretNotesDataDao: SecretNotesDataDao) {

    /**
     * Inserts a new SecretNotesData into the database.
     *
     * @param secretNotesData The SecretNotesData to be inserted.
     */
    suspend fun insert(secretNotesData: SecretNotesData) {
        secretNotesDataDao.insert(secretNotesData)
    }

    /**
     * Inserts or updates a SecretNotesData based on matching id and userId.
     *
     * @param secretNotesData The SecretNotesData to be upserted.
     */
    suspend fun upsert(secretNotesData: SecretNotesData) {
        secretNotesDataDao.upsert(secretNotesData)
    }

    /**
     * Retrieves all SecretNotesData associated with a specific userId.
     *
     * @param userId The ID of the user whose notes should be retrieved.
     * @return A Flow emitting a list of SecretNotesData for the given userId.
     */
    fun getAll(userId: String): Flow<List<SecretNotesData>> = flow {
        emit(secretNotesDataDao.getAll(userId))
    }

    /**
     * Retrieves a specific SecretNotesData by its id.
     *
     * @param id The ID of the SecretNotesData to be retrieved.
     * @return A Flow emitting the SecretNotesData with the given ID, or null if no such note exists.
     */
    fun getById(id: Int): Flow<SecretNotesData?> = flow {
        emit(secretNotesDataDao.getById(id))
    }

    /**
     * Retrieves all SecretNotesData where isSynced is false.
     *
     * @return A Flow emitting a list of SecretNotesData where isSynced is false.
     */
    fun getAllWhereIsNotSynced(): Flow<List<SecretNotesData>> = flow {
        emit(secretNotesDataDao.getAllWhereIsNotSynced())
    }

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
    suspend fun update(
        id: Int,
        title: String,
        summary: String,
        content: String,
        timestamp: Long,
        isSynced: Boolean,
        isSecret: Boolean,
        userProtectionEnabled: Boolean
    ) {
        secretNotesDataDao.update(id, title, summary, content, timestamp, isSynced, isSecret, userProtectionEnabled)
    }

    /**
     * Updates the timestamp of a specific SecretNotesData.
     *
     * @param id The ID of the SecretNotesData to be updated.
     * @param timestamp The new timestamp for the SecretNotesData.
     */
    suspend fun updateTimestamp(id: Int, timestamp: Long) {
        secretNotesDataDao.updateTimestamp(id, timestamp)
    }

    /**
     * Updates the isSynced status of a specific SecretNotesData.
     *
     * @param id The ID of the SecretNotesData to be updated.
     * @param isSynced The new isSynced status for the SecretNotesData.
     */
    suspend fun updateIsSynced(id: Int, isSynced: Boolean) {
        secretNotesDataDao.updateIsSynced(id, isSynced)
    }

    /**
     * Updates the isSecret status of a specific SecretNotesData.
     *
     * @param id The ID of the SecretNotesData to be updated.
     * @param isSecret The new isSecret status for the SecretNotesData.
     */
    suspend fun updateIsSecret(id: Int, isSecret: Boolean) {
        secretNotesDataDao.updateIsSecret(id, isSecret)
    }

    /**
     * Updates the userProtectionEnabled status of a specific SecretNotesData.
     *
     * @param userId The user Id of the SecretNotesData to be updated.
     * @param userProtectionEnabled The new userProtectionEnabled status for the SecretNotesData.
     */
    suspend fun updateUserProtectionEnabled(userId: String, userProtectionEnabled: Boolean) {
        secretNotesDataDao.updateUserProtectionEnabled(userId, userProtectionEnabled)
    }

    /**
     * Deletes all SecretNotesData associated with a specific userId.
     *
     * @param userId The ID of the user whose notes should be deleted.
     */
    suspend fun deleteAll(userId: String) {
        secretNotesDataDao.deleteAll(userId)
    }

    /**
     * Deletes a specific SecretNotesData by its id.
     *
     * @param id The ID of the SecretNotesData to be deleted.
     */
    suspend fun deleteById(id: Int) {
        secretNotesDataDao.deleteById(id)
    }
}