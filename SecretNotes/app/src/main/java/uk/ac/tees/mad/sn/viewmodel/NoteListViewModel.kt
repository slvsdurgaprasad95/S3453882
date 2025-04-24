package uk.ac.tees.mad.sn.viewmodel

import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uk.ac.tees.mad.sn.model.dataclass.room.SecretNotesData
import uk.ac.tees.mad.sn.model.datastore.DataStoreManager
import uk.ac.tees.mad.sn.model.repository.SecretNotesDataRepository
import uk.ac.tees.mad.sn.utils.Constants.NOTES
import uk.ac.tees.mad.sn.utils.Constants.USERS

class NoteListViewModel(
    private val repository: SecretNotesDataRepository,
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val dataStoreManager: DataStoreManager,
): ViewModel() {
    private val userId = auth.currentUser?.uid?:""

    private val _noteList = MutableStateFlow(emptyList<SecretNotesData>())
    val noteList: StateFlow<List<SecretNotesData>> get() = _noteList

    private val _isFingerprintLock = MutableStateFlow(false)
    val isFingerprintLock:StateFlow<Boolean> get() = _isFingerprintLock

    init {
        fetchData()
        syncData()
        viewModelScope.launch {
            dataStoreManager.isFingerprintLockFlow.collect {
                _isFingerprintLock.value = it
            }
        }
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

    fun authenticate(activity: FragmentActivity, onComplete:(Boolean)-> Unit) {
        val executor = ContextCompat.getMainExecutor(activity)
        val biometricPrompt = BiometricPrompt(activity, executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                onComplete(true)
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                onComplete(false)
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
               onComplete(false)
            }
        })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Authentication")
            .setSubtitle("Login using fingerprint")
            .setAllowedAuthenticators(
                BiometricManager.Authenticators.BIOMETRIC_STRONG or
                        BiometricManager.Authenticators.DEVICE_CREDENTIAL

            )
            .build()

        biometricPrompt.authenticate(promptInfo)
    }
}