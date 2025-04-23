package uk.ac.tees.mad.sn.model.repository

import kotlinx.coroutines.flow.Flow
import uk.ac.tees.mad.sn.model.dataclass.room.SecretNotesData
import uk.ac.tees.mad.sn.model.room.SecretNotesDataDao

/**
 * Repository class that handles data operations for SecretNotesData.
 *
 * This repository abstracts the data access from the underlying data source (Room database)
 * and provides a clean API for interacting with SecretNotesData.
 */
class SecretNotesDataRepository(private val dao: SecretNotesDataDao) {

    suspend fun addNote(entity: SecretNotesData){
        dao.addNote(entity)
    }

    fun getNoteById(id: String): Flow<SecretNotesData?>{
        return dao.getNoteById(id)
    }

    fun getAllNotes(userId: String):Flow<List<SecretNotesData>>{
        return dao.getAllNotes(userId)
    }

    suspend fun deleteNote(id: String){
        dao.deleteNote(id)
    }
}