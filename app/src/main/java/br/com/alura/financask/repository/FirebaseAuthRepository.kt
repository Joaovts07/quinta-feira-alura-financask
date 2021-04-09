package br.com.alura.financask.repository

import android.util.Log
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

    suspend fun register(user: User) {
        try {
            val task =
                    firebaseAuth.createUserWithEmailAndPassword(user.email, user.password)
            task.addOnSuccessListener {
                Log.i(TAG, "cadastra: cadastro sucedido")
                //liveData.value = Resource(true)
            }
            task.addOnFailureListener { exception ->
                Log.e(TAG, "cadastra: cadastro falhou", exception)
                val error: String = returnRegisterError(exception)
                //liveData.value = Resource(false, mensagemErro)
            }
        } catch (exception: Exception) {

        }
    }

    private fun returnRegisterError(exception: Exception): String = when (exception) {
        is FirebaseAuthInvalidUserException,
        is FirebaseAuthInvalidCredentialsException -> "E-mail ou senha incorretos"
        else -> "Erro desconhecido"
    }

}