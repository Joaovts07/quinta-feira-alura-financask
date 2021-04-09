package br.com.alura.financask.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.alura.financask.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseUser

private const val TAG = "FirebaseAuthRepository"

class FirebaseAuthRepository(private val firebaseAuth: FirebaseAuth) {

    fun desloga() {
        firebaseAuth.signOut()
    }

    fun isLoged(): Boolean {
        val firebaseUser: FirebaseUser? = firebaseAuth.currentUser
        if (firebaseUser != null) {
            return true
        }
        return false
    }

    fun authenticate(usuario: User): LiveData<Resource<Boolean>> {
        val liveData = MutableLiveData<Resource<Boolean>>()
        try {
            firebaseAuth.signInWithEmailAndPassword(usuario.email, usuario.password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            liveData.value = Resource(true)
                        } else {
                            Log.e(TAG, "autentica: ", task.exception)
                            val mensagemErro: String = returnRegisterError(task.exception)
                            liveData.value = Resource(false, mensagemErro)
                        }
                    }
        } catch (e: IllegalArgumentException) {
            liveData.value = Resource(false, "E-mail ou senha não pode ser vazio")
        }
        return liveData
    }

    fun register(user: User): LiveData<Resource<Boolean>> {
        val liveData = MutableLiveData<Resource<Boolean>>()

        try {
            val task =
                    firebaseAuth.createUserWithEmailAndPassword(user.email, user.password)
            task.addOnSuccessListener {
                Log.i(TAG, "cadastra: cadastro sucedido")
                liveData.value = Resource(true)

            }
            task.addOnFailureListener { exception ->
                Log.e(TAG, "cadastra: cadastro falhou", exception)
                val error: String = returnRegisterError(exception)
                liveData.value = Resource(false, error)
            }
        } catch (exception: Exception) {
            liveData.value = Resource(false, "E-mail ou senha não ser vazio")

        }
        return liveData
    }

    private fun returnRegisterError(exception: java.lang.Exception?): String = when (exception) {
        is FirebaseAuthInvalidUserException,
        is FirebaseAuthInvalidCredentialsException -> "E-mail ou senha incorretos"
        else -> "Erro desconhecido"
    }

}