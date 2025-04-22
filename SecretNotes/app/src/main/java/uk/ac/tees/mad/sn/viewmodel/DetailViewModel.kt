package uk.ac.tees.mad.sn.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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

    fun saveNote(title: String, summery: String, content: String, isLocked: Boolean,context: Context ,onComplete:()-> Unit){
        viewModelScope.launch {
            val entity = SecretNotesData(
                id = "",
                userId = userId,
                isLocked = isLocked,
                title = title,
                summary = summery,
                content = content,
                timestamp = System.currentTimeMillis()
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
}