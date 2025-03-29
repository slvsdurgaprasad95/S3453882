package uk.ac.tees.mad.sn.ui.screens

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel
import uk.ac.tees.mad.sn.R
import uk.ac.tees.mad.sn.model.dataclass.state.LoadingState
import uk.ac.tees.mad.sn.view.navigation.Dest
import uk.ac.tees.mad.sn.view.navigation.SubGraph
import uk.ac.tees.mad.sn.view.utils.LoadingErrorScreen
import uk.ac.tees.mad.sn.viewmodel.SplashScreenViewModel

@Composable
fun SplashScreen(
    navController: NavHostController, viewmodel: SplashScreenViewModel = koinViewModel()
) {
    val loadingState by viewmodel.loadingState.collectAsStateWithLifecycle()
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    color = Color.Black
                ), contentAlignment = Alignment.Center
        ) {
            Crossfade(
                targetState = loadingState,
                animationSpec = tween(durationMillis = 1000),
                label = "splashScreen"
            ) { state ->
                when (state) {
                    is LoadingState.Error -> {
                        LoadingErrorScreen(
                            errorMessage = state.message,
                            onRetry = { viewmodel.startLoading() })
                    }

                    is LoadingState.Loading -> {
                        Splash()
                    }

                    is LoadingState.Success -> {
                        LaunchedEffect(key1 = Unit) {
                            if (viewmodel.isSignedIn()) {
                                navController.navigate(SubGraph.HomeGraph) {
                                    popUpTo(Dest.SplashScreen) {
                                        inclusive = true
                                    }
                                }
                            } else {
                            navController.navigate(SubGraph.AuthGraph) {
                                popUpTo(Dest.SplashScreen) {
                                    inclusive = true
                                }
                            }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Splash() {
    val logoScale = remember { Animatable(0f) }
    val textAlpha = remember { Animatable(0f) }
    val tagLineAndProgressbarAlpha = remember { Animatable(0f) }

    LaunchedEffect(key1 = true) {
        // Logo animation
        logoScale.animateTo(
            targetValue = 1f, animationSpec = tween(durationMillis = 1000)
        )
        // App name animation
        delay(500) // Stagger the animation
        textAlpha.animateTo(
            targetValue = 1f, animationSpec = tween(durationMillis = 1000)
        )
        // Tagline animation
        delay(500) // Stagger the animation
        tagLineAndProgressbarAlpha.animateTo(
            targetValue = 1f, animationSpec = tween(durationMillis = 1000)
        )

    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier
                .size(150.dp)
                .scale(logoScale.value),
            painter = painterResource(id = R.drawable.secretnotes_logo),
            contentDescription = "App Logo",
            colorFilter = ColorFilter.tint(Color.White)
        )
        Text(
            text = "SecretNotes",
            color = Color.White,
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(top = 20.dp)
                .alpha(textAlpha.value)
        )
        Text(
            text = "Private Notes, Protected by You",
            color = Color.White,
            fontSize = 20.sp,
            modifier = Modifier
                .padding(top = 10.dp)
                .alpha(tagLineAndProgressbarAlpha.value)
        )
        LinearProgressIndicator(
            modifier = Modifier
                .padding(top = 20.dp)
                .alpha(tagLineAndProgressbarAlpha.value),
            color = Color.White,
            trackColor = Color.Gray
        )
    }
}