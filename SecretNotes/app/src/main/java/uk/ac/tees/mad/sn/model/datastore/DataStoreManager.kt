package uk.ac.tees.mad.sn.model.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager(private val dataStore:DataStore<Preferences>) {

    companion object {
        val IS_DARK_MODE_KEY = booleanPreferencesKey("is_dark_mode")
        val IS_FINGERPRINT_LOCK_KEY = booleanPreferencesKey("is_fingerprint_lock")
    }

    suspend fun saveDarkModeStatus(isDarkMode:Boolean){
        dataStore.edit { preferences->
            preferences[IS_DARK_MODE_KEY] =isDarkMode
        }
    }

    suspend fun saveFingerprintLockStatus(isFingerLock:Boolean){
        dataStore.edit { preferences->
            preferences[IS_FINGERPRINT_LOCK_KEY] = isFingerLock
        }
    }

    val isDarkModeFlow:Flow<Boolean> = dataStore.data.map {
            preferences->
        preferences[IS_DARK_MODE_KEY] == true
    }

    val isFingerprintLockFlow:Flow<Boolean> = dataStore.data.map {
            preferences ->
        preferences[IS_FINGERPRINT_LOCK_KEY] == true
    }
}