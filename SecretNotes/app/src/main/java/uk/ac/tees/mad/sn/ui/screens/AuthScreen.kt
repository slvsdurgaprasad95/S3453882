package uk.ac.tees.mad.sn.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.HowToReg
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LeadingIconTab
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import org.koin.androidx.compose.koinViewModel
import uk.ac.tees.mad.sn.R
import uk.ac.tees.mad.sn.model.dataclass.firebase.AuthResult
import uk.ac.tees.mad.sn.view.navigation.Dest
import uk.ac.tees.mad.sn.view.navigation.SubGraph
import uk.ac.tees.mad.sn.viewmodel.AuthScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    navController: NavHostController, viewmodel: AuthScreenViewModel = koinViewModel()
) {
    val email by viewmodel.email.collectAsStateWithLifecycle()
    val password by viewmodel.password.collectAsStateWithLifecycle()
    val isPasswordVisible by viewmodel.isPasswordVisible.collectAsStateWithLifecycle()
    val isSignInMode by viewmodel.isSignInMode.collectAsStateWithLifecycle()
    val isRegisterMode by viewmodel.registerMode.collectAsStateWithLifecycle()
    val signInResult by viewmodel.signInResult.collectAsStateWithLifecycle()
    val registerResult by viewmodel.registerResult.collectAsStateWithLifecycle()

    val focusManager = LocalFocusManager.current
    val focusRequesterEmail = remember { FocusRequester() }
    val focusRequesterPassword = remember { FocusRequester() }

    val state by viewmodel.tabState.collectAsStateWithLifecycle()
    val titlesAndIcons = viewmodel.titlesAndIcons

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Background Image
            Image(
                painter = painterResource(id = R.drawable.authscreen_background),
                contentDescription = "Background Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
            AnimatedVisibility(isSignInMode) {
                when (val result = signInResult) {
                    is AuthResult.Loading -> {
                        AlertDialog(onDismissRequest = {
                            viewmodel.switchSignInMode()
                        }, icon = {
                            Icon(
                                Icons.AutoMirrored.Filled.Login,
                                contentDescription = null,
                                modifier = Modifier.size(48.dp),
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }, title = {
                            Text(
                                text = "Signing In",
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }, text = {
                            Column(modifier = Modifier.fillMaxWidth()) {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .size(48.dp)
                                        .align(Alignment.CenterHorizontally)
                                )
                            }
                        }, confirmButton = { })
                    }

                    is AuthResult.Success -> {
                        // Handle successful sign-up
                        navController.navigate(Dest.ProfileScreen) {
                            popUpTo(SubGraph.AuthGraph) {
                                inclusive = true
                            }
                        }
                    }

                    is AuthResult.Error -> {
                        // Handle sign-in error
                        AlertDialog(icon = {
                            Icon(
                                Icons.Default.Error,
                                contentDescription = null,
                                modifier = Modifier.size(48.dp),
                                tint = MaterialTheme.colorScheme.error
                            )
                        }, title = {
                            Text(
                                text = "Error",
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }, text = {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = result.exception.message.toString(),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }, confirmButton = {
                            TextButton(onClick = {
                                viewmodel.switchSignInMode()
                            }) {
                                Text(text = "Retry?", fontWeight = FontWeight.Bold)
                            }
                        }, onDismissRequest = {
                            viewmodel.switchSignInMode()
                        })
                    }
                }
            }

            AnimatedVisibility(isRegisterMode) {
                when (val result = registerResult) {
                    is AuthResult.Loading -> {
                        AlertDialog(onDismissRequest = {
                            viewmodel.switchRegisterMode()
                        }, icon = {
                            Icon(
                                Icons.Default.CloudUpload,
                                contentDescription = null,
                                modifier = Modifier.size(48.dp),
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }, title = {
                            Text(
                                text = "Registering...",
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }, text = {
                            Column(modifier = Modifier.fillMaxWidth()) {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .size(48.dp)
                                        .align(Alignment.CenterHorizontally)
                                )
                            }
                        }, confirmButton = { })
                    }

                    is AuthResult.Success -> {
                        // Handle successful register
                        AlertDialog(icon = {
                            Icon(
                                Icons.Default.CloudDone,
                                contentDescription = null,
                                modifier = Modifier.size(48.dp),
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }, title = {
                            Text(
                                text = "Register Successful",
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }, text = {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = "You have successfully registered.",
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }, confirmButton = {
                            TextButton(onClick = {
                                viewmodel.switchTabState()
                                viewmodel.switchRegisterMode()
                            }) {
                                Text(
                                    text = "Sign In", fontWeight = FontWeight.Bold
                                )
                            }
                        }, onDismissRequest = {
                            viewmodel.switchRegisterMode()
                            viewmodel.switchTabState()
                        })

                    }

                    is AuthResult.Error -> {
                        // Handle register error
                        AlertDialog(icon = {
                            Icon(
                                Icons.Default.Error,
                                contentDescription = null,
                                modifier = Modifier.size(48.dp),
                                tint = MaterialTheme.colorScheme.error
                            )
                        }, title = {
                            Text(
                                text = "Error",
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }, text = {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(result.exception.message.toString())
                            }
                        }, confirmButton = {
                            TextButton(onClick = {
                                viewmodel.switchRegisterMode()
                            }) {
                                Text(text = "Retry?", fontWeight = FontWeight.Bold)
                            }
                        }, onDismissRequest = {
                            viewmodel.switchRegisterMode()
                        })
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Elevated Card
                Card(
                    modifier = Modifier
                        .fillMaxSize(0.9f)
                        .padding(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(
                        4.dp, brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color.Gray, Color.White
                            ),
                            startX = 0f,
                            endX = 1000f,
                            tileMode = TileMode.Mirror,
                        )
                    )
                ) {
                    PrimaryTabRow(selectedTabIndex = state) {
                        titlesAndIcons.forEachIndexed { index, (title, icon) ->
                            LeadingIconTab(
                                selected = state == index,
                                onClick = { viewmodel.updateTabState(index) },
                                text = {
                                    Text(title)

                                },
                                icon = { Icon(icon, contentDescription = null) })
                        }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(160.dp)
                                .background(
                                    color = Color.White, shape = MaterialTheme.shapes.extraLarge
                                )
                                .padding(16.dp)
                        ) {
                            if (state == 0) {
                                Image(
                                    modifier = Modifier.fillMaxSize(),
                                    painter = painterResource(id = R.drawable.signin_image),
                                    contentDescription = "Welcome Image"
                                )
                            } else {
                                Image(
                                    modifier = Modifier.fillMaxSize(),
                                    painter = painterResource(id = R.drawable.register_image),
                                    contentDescription = "Welcome Image"
                                )
                            }
                        }
                        Text(
                            text = if (state == 0) "Sign In" else "Register",
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 8.dp),
                        )
                        // Email TextField
                        OutlinedTextField(
                            value = email,
                            modifier = Modifier
                                .fillMaxWidth()
                                .focusRequester(focusRequesterEmail),
                            onValueChange = {
                                viewmodel.updateEmail(it)
                            },
                            label = {
                                Text(
                                    text = "Email"
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    Icons.Filled.Email,
                                    contentDescription = "Email",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Email, imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(onNext = {
                                focusRequesterPassword.requestFocus()
                            }),
                            shape = RoundedCornerShape(16.dp),
                            singleLine = true
                        )

                        // Password TextField
                        OutlinedTextField(
                            value = password,
                            onValueChange = {
                                viewmodel.updatePassword(it)
                            },
                            label = {
                                Text(
                                    text = "Password"
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    Icons.Filled.Lock,
                                    contentDescription = "Password",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .focusRequester(focusRequesterPassword),
                            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password, imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(onDone = {
                                focusManager.clearFocus()
                            }),
                            shape = RoundedCornerShape(16.dp),
                            singleLine = true,
                            trailingIcon = {
                                IconButton(onClick = {
                                    viewmodel.togglePasswordVisibility()
                                }) {
                                    Icon(
                                        imageVector = if (isPasswordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                                        contentDescription = "Toggle Password Visibility",
                                        tint = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            })

                        Spacer(modifier = Modifier.height(24.dp))

                        Button(
                            enabled = email.isNotBlank() && password.isNotBlank(), onClick = {
                                if (state == 0) {
                                    viewmodel.signIn(email, password)
                                } else {
                                    viewmodel.register(email, password)
                                }
                            }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp)
                        ) {
                            if (state == 0) {
                                Icon(
                                    Icons.AutoMirrored.Filled.Login,
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp),
                                )
                            } else {
                                Icon(
                                    Icons.Filled.HowToReg,
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp),
                                )
                            }
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = if (state == 0) "Sign In" else "Register",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (state == 0) "Don't have an account?" else "Already have an account?",
                                textAlign = TextAlign.Center,
                            )
                            TextButton(onClick = {
                                viewmodel.switchTabState()
                            }) {
                                if (state == 0) {
                                    Icon(
                                        Icons.Default.HowToReg,
                                        contentDescription = null,
                                        modifier = Modifier.size(24.dp),
                                    )
                                } else {
                                    Icon(
                                        Icons.AutoMirrored.Filled.Login,
                                        contentDescription = null,
                                        modifier = Modifier.size(24.dp),
                                    )
                                }
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = if (state == 0) "Register" else "Sign In",
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}