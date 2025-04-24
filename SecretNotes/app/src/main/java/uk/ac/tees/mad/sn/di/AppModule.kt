package uk.ac.tees.mad.sn.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import uk.ac.tees.mad.sn.model.datastore.DataStoreManager
import uk.ac.tees.mad.sn.model.network.NetworkConnectivityManager
import uk.ac.tees.mad.sn.model.repository.AuthRepository
import uk.ac.tees.mad.sn.model.repository.NetworkRepository
import uk.ac.tees.mad.sn.model.repository.SecretNotesDataRepository
import uk.ac.tees.mad.sn.model.room.SecretNotesDatabase
import uk.ac.tees.mad.sn.viewmodel.AuthScreenViewModel
import uk.ac.tees.mad.sn.viewmodel.DetailViewModel
import uk.ac.tees.mad.sn.viewmodel.NoteListViewModel
import uk.ac.tees.mad.sn.viewmodel.ProfileScreenViewModel
import uk.ac.tees.mad.sn.viewmodel.SplashScreenViewModel
import java.util.concurrent.Executor
import java.util.concurrent.Executors

private val Context.dataStore by preferencesDataStore(name = "app_preferences")
val appModule = module {
    // TrustedTime API
    includes(trustedTimeModule)

    // Network
    single { NetworkConnectivityManager(androidContext()) }
    single { NetworkRepository(get()) }

    // Firebase
    single { FirebaseAuth.getInstance() }
    single { AuthRepository(get()) }
    single { FirebaseFirestore.getInstance() }

    // SecretNotesDatabase
    single {
        Room.databaseBuilder(
            androidApplication(), SecretNotesDatabase::class.java, "secret_notes_database"
        ).build()
    }
    single {
        val database = get<SecretNotesDatabase>()
        database.secretNotesDataDao()
    }
    single{ SecretNotesDataRepository(get()) }

    // Datastore
    single<DataStore<Preferences>> { androidContext().dataStore }
    single { DataStoreManager(get()) }

    // ViewModels
    viewModelOf(::SplashScreenViewModel)
    viewModelOf(::AuthScreenViewModel)
    viewModelOf(::ProfileScreenViewModel)
    viewModelOf(::DetailViewModel)
    viewModelOf(::NoteListViewModel)
}