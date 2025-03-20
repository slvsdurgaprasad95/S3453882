package uk.ac.tees.mad.sn.model.repository

import kotlinx.coroutines.flow.Flow
import uk.ac.tees.mad.sn.model.network.NetworkConnectivityManager

class NetworkRepository(private val networkConnectivityManager: NetworkConnectivityManager) {
    val isNetworkAvailable: Flow<Boolean> = networkConnectivityManager.observeConnectivity()
}