package uk.ac.tees.mad.sn.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uk.ac.tees.mad.sn.model.dataclass.room.SecretNotesData
import uk.ac.tees.mad.sn.model.repository.SecretNotesDataRepository
import uk.ac.tees.mad.sn.utils.Constants.NOTES
import uk.ac.tees.mad.sn.utils.Constants.USERS

class NoteListViewModel(
    private val repository: SecretNotesDataRepository,
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth
): ViewModel() {
    private val userId = auth.currentUser?.uid?:""

    private val _noteList = MutableStateFlow(emptyList<SecretNotesData>())
    val noteList: StateFlow<List<SecretNotesData>> get() = _noteList
    init {
        fetchData()
        syncData()
    }
    private fun fetchData(){
        viewModelScope.launch {
            repository.getAllNotes(userId).collect {
                _noteList.value = it
            }
        }
    }

    private fun syncData(){
        db.collection(USERS)
            .document(userId)
            .collection(NOTES)
            .get()
            .addOnSuccessListener { documents->
                documents.forEach { doc->
                    val mNote = doc.toObject(SecretNotesData::class.java)
                    viewModelScope.launch {
                        repository.addNote(mNote)
                    }
                }
            }
    }
}