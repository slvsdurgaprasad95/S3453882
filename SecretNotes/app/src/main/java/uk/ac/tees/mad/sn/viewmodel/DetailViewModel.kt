package uk.ac.tees.mad.sn.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import uk.ac.tees.mad.sn.model.dataclass.room.SecretNotesData
import uk.ac.tees.mad.sn.model.repository.SecretNotesDataRepository
import uk.ac.tees.mad.sn.utils.Constants.NOTES
import uk.ac.tees.mad.sn.utils.Constants.USERS

class DetailViewModel(
    private val repository: SecretNotesDataRepository,
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth
): ViewModel() {

    val userId = auth.currentUser?.uid?:""

    private val _title = MutableStateFlow("")
    val title: StateFlow<String> get() = _title

    private val _summery = MutableStateFlow("")
    val summery: StateFlow<String> get() = _summery

    private val _content = MutableStateFlow("")
    val content: StateFlow<String> get() = _content

    private val _isLocked = MutableStateFlow(false)
    val isLocked: StateFlow<Boolean> get() = _isLocked

    fun saveNote(context: Context ,onComplete:()-> Unit){
        viewModelScope.launch {
            val entity = SecretNotesData(
                id = "",
                userId = userId,
                isLocked = _isLocked.value,
                title = _title.value,
                summary = _summery.value,
                content = _content.value,
            )
            db.collection(USERS)
                .document(userId)
                .collection(NOTES)
                .add(entity)
                .addOnSuccessListener { document->
                    document.update("id", document.id)
                    viewModelScope.launch {
                        repository.addNote(entity.copy(id = document.id))
                        Toast.makeText(context,"Note successfully added", Toast.LENGTH_SHORT).show()
                        onComplete()
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(context,"Failed to add note", Toast.LENGTH_SHORT).show()
                }
        }
    }

    fun updateNote(id:String,context: Context ,onComplete:()-> Unit){
        viewModelScope.launch {
            val entity = SecretNotesData(
                id = id,
                userId = userId,
                isLocked = _isLocked.value,
                title = _title.value,
                summary = _summery.value,
                content = _content.value,
            )
            db.collection(USERS)
                .document(userId)
                .collection(NOTES)
                .document(id)
                .set(entity, SetOptions.merge())
                .addOnSuccessListener { document->
                    viewModelScope.launch {
                        repository.addNote(entity)
                        Toast.makeText(context,"Note successfully saved", Toast.LENGTH_SHORT).show()
                        onComplete()
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(context,"Failed to save note", Toast.LENGTH_SHORT).show()
                }
        }
    }

    fun getNote(id: String){
        viewModelScope.launch {
            val note = repository.getNoteById(id).first()
            if (note!=null){
                _title.value = note.title
                _summery.value = note.summary
                _content.value = note.content
                _isLocked.value = note.isLocked
            }
        }
    }

    fun onTitleChange(value: String){
        _title.value = value
    }

    fun onSummeryChange(value: String){
        _summery.value = value
    }

    fun onContentChange(value: String){
        _content.value = value
    }

    fun onIsLockedChange(value: Boolean){
        _isLocked.value = value
    }

    fun deleteNote(id: String, onComplete:()-> Unit){
        db.collection(USERS)
            .document(userId)
            .collection(NOTES)
            .document(id)
            .delete()
            .addOnSuccessListener {
                viewModelScope.launch {
                    repository.deleteNote(id)
                    onComplete()
                }
            }

    }
}