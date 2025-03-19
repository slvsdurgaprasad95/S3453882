package uk.ac.tees.mad.sn.di

import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import uk.ac.tees.mad.sn.model.network.NetworkConnectivityManager
import uk.ac.tees.mad.sn.model.repository.AuthRepository
import uk.ac.tees.mad.sn.model.repository.NetworkRepository
import uk.ac.tees.mad.sn.viewmodel.SplashScreenViewModel

val appModule = module {
    // TrustedTime API
    includes(trustedTimeModule)

    // Network
    single { NetworkConnectivityManager(androidContext()) }
    single { NetworkRepository(get()) }

    // Firebase
    single { FirebaseAuth.getInstance() }
    single { AuthRepository(get()) }

    // ViewModels
    viewModelOf(::SplashScreenViewModel)
}