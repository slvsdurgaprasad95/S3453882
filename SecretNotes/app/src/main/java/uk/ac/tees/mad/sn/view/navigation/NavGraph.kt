package uk.ac.tees.mad.sn.view.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import uk.ac.tees.mad.sn.model.time.TrustedTimeManager
import uk.ac.tees.mad.sn.ui.screens.AuthScreen
import uk.ac.tees.mad.sn.ui.screens.NoteDetailScreen
import uk.ac.tees.mad.sn.ui.screens.NotesListScreen
import uk.ac.tees.mad.sn.ui.screens.ProfileScreen
import uk.ac.tees.mad.sn.ui.screens.SplashScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController, trustedTimeManager: TrustedTimeManager
) {
    NavHost(
        navController = navController, startDestination = Dest.SplashScreen
    ) {
        composable<Dest.SplashScreen> {
            SplashScreen(navController = navController)
        }
        navigation<SubGraph.AuthGraph>(startDestination = Dest.AuthScreen) {
            composable<Dest.AuthScreen> {
                AuthScreen(navController = navController)
            }
        }
        navigation<SubGraph.HomeGraph>(startDestination = Dest.NotesListScreen) {
            composable<Dest.NotesListScreen> {
                NotesListScreen(navController = navController)
            }
            composable<Dest.NoteDetailScreen> {
                val args = it.toRoute<Dest.NoteDetailScreen>()
                NoteDetailScreen(
                    navController = navController, noteTitle = args.noteTitle
                )
            }
//            composable<Dest.DetailsScreen> {
//                val args = it.toRoute<Dest.DetailsScreen>()
//                DetailsScreen(
//                    navController = navController, symbol = args.symbol
//                )
//            }
            composable<Dest.ProfileScreen> {
                ProfileScreen(navController = navController)
            }
        }
    }
}