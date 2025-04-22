package uk.ac.tees.mad.sn.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import org.koin.androidx.compose.koinViewModel
import uk.ac.tees.mad.sn.viewmodel.DetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailScreen(
    navController: NavHostController,
    viewModel: DetailViewModel = koinViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val isLocked = remember { mutableStateOf(false) }
    var title by remember { mutableStateOf("") }
    var summary by remember { mutableStateOf("") }
    var noteContent by remember { mutableStateOf("") }
    val context = LocalContext.current
    Scaffold(
        modifier = Modifier.fillMaxSize(), topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Spacer(modifier = Modifier.padding(horizontal = 8.dp))
                        Text(
                            text = "Save/Edit Note",
                            maxLines = 1,
                            fontSize = 30.sp,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }, navigationIcon = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        IconButton(onClick = {
                            navController.popBackStack()
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                modifier = Modifier.size(36.dp),
                            )
                        }
                    }
                }, actions = {
                    IconButton(onClick = {
                        isLocked.value = !isLocked.value
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Lock,
                            contentDescription = "lock",
                            modifier = Modifier.size(36.dp),
                            tint = if (isLocked.value) Color.Blue else MaterialTheme.colorScheme.onSurface
                        )
                    }
                    IconButton(onClick = {

                    }) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Delete",
                            modifier = Modifier.size(36.dp),
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }, scrollBehavior = scrollBehavior
            )
        }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = summary,
                onValueChange = { summary = it },
                label = { Text("Summary") },
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = noteContent,
                onValueChange = { noteContent = it },
                label = { Text("Note") },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f), // Allow the note field to take up remaining space
            )
            TextButton({
                viewModel.saveNote(
                    title = title,
                    summery = summary,
                    content = noteContent,
                    isLocked = isLocked.value,
                    context = context
                ) {
                    navController.popBackStack()
                }
            }, modifier = Modifier.padding(16.dp).align(Alignment.CenterHorizontally)) {
                Text(
                    "SAVE",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(6.dp)
                )
            }
        }
    }
}