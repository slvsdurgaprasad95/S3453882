package uk.ac.tees.mad.sn.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import uk.ac.tees.mad.sn.model.dataclass.room.SecretNotesData

@Database(entities = [SecretNotesData::class], version = 1, exportSchema = false)
abstract class SecretNotesDatabase : RoomDatabase() {
    abstract fun secretNotesDataDao(): SecretNotesDataDao
}