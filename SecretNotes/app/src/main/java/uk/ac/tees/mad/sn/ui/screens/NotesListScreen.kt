package uk.ac.tees.mad.sn.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import org.koin.androidx.compose.koinViewModel
import uk.ac.tees.mad.sn.R
import uk.ac.tees.mad.sn.model.dataclass.room.SecretNotesData
import uk.ac.tees.mad.sn.view.navigation.Dest
import uk.ac.tees.mad.sn.viewmodel.NoteListViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// Define a list of colors with good contrast ratios
val cardColors = listOf(
    Color(0xFFF44336), // Red 500
    Color(0xFFE91E63), // Pink 500
    Color(0xFF9C27B0), // Purple 500
    Color(0xFF673AB7), // Deep Purple 500
    Color(0xFF3F51B5), // Indigo 500
    Color(0xFF2196F3), // Blue 500
    Color(0xFF03A9F4), // Light Blue 500
    Color(0xFF00BCD4), // Cyan 500
    Color(0xFF009688), // Teal 500
    Color(0xFF4CAF50), // Green 500
    Color(0xFF8BC34A), // Light Green 500
    Color(0xFFCDDC39), // Lime 500
    Color(0xFFFFEB3B), // Yellow 500
    Color(0xFFFFC107), // Amber 500
    Color(0xFFFF9800), // Orange 500
    Color(0xFFEF6C00)  // Deep Orange 500
)

// Function to get color based on index
fun getCardColor(index: Int): Color {
    return cardColors[index % cardColors.size]
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NotesListScreen(
    navController: NavHostController,
    viewModel: NoteListViewModel = koinViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val noteList by viewModel.noteList.collectAsState()
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(
            title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                //Spacer(modifier = Modifier.padding(horizontal = 8.dp))
                Image(
                    painter = painterResource(id = R.drawable.secretnotes_logo),
                    contentDescription = "App Logo",
                    modifier = Modifier.size(36.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
                )
                Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                Text(
                    text = "SecretNotes",
                    maxLines = 1,
                    fontSize = 30.sp,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }, navigationIcon = {
        }, actions = {
            IconButton(onClick = { navController.navigate(Dest.ProfileScreen) }) {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Profile Screen Icon",
                    modifier = Modifier.size(36.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }, scrollBehavior = scrollBehavior
        )
    }, floatingActionButton = {
        FloatingActionButton(
            onClick = {
                navController.navigate("Detail_Screen/")
            },
        ) {
            Icon(Icons.Filled.Add, "Add Note")
        }
    }) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Adaptive(200.dp), modifier = Modifier.fillMaxSize()
            ) {
                itemsIndexed(noteList) { index, note ->
                    // Display each note as a card with cyclical color
                    NoteCard(
                        note = note, randomColor = getCardColor(index), onCardClick = {
                            navController.navigate("Detail_Screen/${note.id}")
                        })
                }
                item(
                    span = StaggeredGridItemSpan.FullLine
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            modifier = Modifier.size(200.dp),
                            painter = painterResource(id = R.drawable.endoflist_image),
                            contentDescription = "End of List Image"
                        )
                        Text(text = "Houston, we've reached the end of your notes")
                        Spacer(modifier = Modifier.padding(vertical = 28.dp))
                    }

                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NoteCard(note: SecretNotesData, randomColor: Color, onCardClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable(
                onClick = {
                    onCardClick()
                }),
        colors = CardDefaults.cardColors(
            containerColor = randomColor,
        ),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = note.title, style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
//                    color = Color.Black
                )
            )
            Text(
                text = note.summary, style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = SimpleDateFormat("dd/MM/yyyy HH:mm a", Locale.getDefault()).format(Date(note.timestamp)),
                style = MaterialTheme.typography.bodySmall,
//                color = Color.Black,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}