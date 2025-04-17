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
import uk.ac.tees.mad.sn.R
import uk.ac.tees.mad.sn.view.navigation.Dest
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


// Updated data class for Note with date
data class Note(
    val title: String, val summary: String, val date: LocalDateTime
)

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
) {
    // Placeholder list of notes
    val notes = listOf(
        Note(
            "Note 1",
            "Summary for Note 1 with some extra text to demonstrate wrapping",
            LocalDateTime.of(2024, 1, 15, 10, 30)
        ),
        Note("Note 2", "Summary for Note 2", LocalDateTime.of(2024, 2, 20, 14, 0)),
        Note(
            "Note 3",
            "Summary for Note 3 with a very very long text to see the wrap and clipping",
            LocalDateTime.of(2024, 3, 5, 9, 45)
        ),
        Note("Note 4", "Summary for Note 4", LocalDateTime.of(2024, 4, 10, 16, 20)),
        Note("Note 5", "Summary for Note 5", LocalDateTime.of(2024, 5, 2, 11, 15)),
        Note("Note 6", "Summary for Note 6", LocalDateTime.of(2024, 6, 22, 18, 50)),
        Note("Note 7", "Summary for Note 7 with extra text", LocalDateTime.of(2024, 7, 8, 13, 5)),
        Note("Note 8", "Summary for Note 8", LocalDateTime.of(2024, 8, 18, 7, 30)),
        Note(
            "Note 9",
            "Summary for Note 9 with lots of content",
            LocalDateTime.of(2024, 9, 1, 15, 10)
        ),
        Note("Note 10", "Summary for Note 10", LocalDateTime.of(2024, 10, 25, 12, 0)),
    )

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

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
//                Row(
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.Start
//                ) {
//                    Spacer(modifier = Modifier.padding(horizontal = 8.dp))
//                    AnimatedVisibility(offlineMode == true) {
//
//                        Icon(
//                            Icons.Outlined.CloudOff,
//                            "Offline",
//                            tint = MaterialTheme.colorScheme.error
//                        )
//
//                    }
//                    AnimatedVisibility(offlineMode == false) {
//                        Icon(Icons.Outlined.CloudDone, "Online", tint = Color.Green)
//                    }
//                }
        }, actions = {
//                IconButton(onClick = {
//                    navController.navigate(Dest.SearchScreen)
//                }) {
//                    Icon(
//                        imageVector = Icons.Filled.Search,
//                        contentDescription = "Search",
//                        tint = MaterialTheme.colorScheme.onSurface
//                    )
//                }
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
                navController.navigate(Dest.NoteDetailScreen("New Note"))
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
//            items(notes) { note ->
//                // Display each note as a card
//                NoteCard(note = note)
//            }
                itemsIndexed(notes) { index, note ->
                    // Display each note as a card with cyclical color
                    NoteCard(
                        note = note, randomColor = getCardColor(index), onCardClick = {
                            navController.navigate(Dest.NoteDetailScreen(it.title))
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
fun NoteCard(note: Note, randomColor: Color, onCardClick: (Note) -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable(
                onClick = {
                    onCardClick(note)
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
//                color = Color.Black,
                //maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = note.date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                style = MaterialTheme.typography.bodySmall,
//                color = Color.Black,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}