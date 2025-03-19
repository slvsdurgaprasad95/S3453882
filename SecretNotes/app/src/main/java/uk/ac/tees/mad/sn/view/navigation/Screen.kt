package uk.ac.tees.mad.sn.view.navigation

import kotlinx.serialization.Serializable

sealed class SubGraph {
    @Serializable
    data object AuthGraph : SubGraph()

    @Serializable
    data object HomeGraph : SubGraph()
}

sealed class Dest {
    @Serializable
    data object SplashScreen : Dest()

    @Serializable
    data object AuthScreen : Dest()

    @Serializable
    data object SignUpScreen : Dest()

    @Serializable
    data object HomeScreen : Dest()

    @Serializable
    data object SearchScreen : Dest()

    @Serializable
    data class DetailsScreen(val symbol: String) : Dest()

    @Serializable
    data object ProfileScreen : Dest()
}