package uk.ac.tees.mad.sn.ui.screens

import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
    id: String,
    navController: NavHostController,
    viewModel: DetailViewModel = koinViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val isLocked by viewModel.isLocked.collectAsState()
    val title by viewModel.title.collectAsState()
    val summary by viewModel.summery.collectAsState()
    val noteContent by viewModel.content.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(id) {
        if (!id.isEmpty()) {
            viewModel.getNote(id)
        }
    }

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
                            text = if(id.isEmpty())"Add Note" else "Edit Note",
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
                        viewModel.onIsLockedChange(!isLocked)
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Lock,
                            contentDescription = "lock",
                            modifier = Modifier.size(36.dp),
                            tint = if (isLocked) Color.Blue else MaterialTheme.colorScheme.onSurface
                        )
                    }
                    if (!id.isEmpty()) {
                        IconButton(onClick = {
                            viewModel.deleteNote(id){
                                Toast.makeText(context, "Notes deleted", Toast.LENGTH_SHORT).show()
                                navController.popBackStack()
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = "Delete",
                                modifier = Modifier.size(36.dp),
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
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
                onValueChange = {viewModel.onTitleChange(it) },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = summary,
                onValueChange = { viewModel.onSummeryChange(it) },
                label = { Text("Summary") },
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = noteContent,
                onValueChange = { viewModel.onContentChange(it) },
                label = { Text("Note") },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f), // Allow the note field to take up remaining space
            )
            TextButton({
                if (id.isEmpty()){
                    viewModel.saveNote(context = context) {
                        navController.popBackStack()
                    }
                }
                else{
                    viewModel.updateNote(id,context) {
                        navController.popBackStack()
                    }
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