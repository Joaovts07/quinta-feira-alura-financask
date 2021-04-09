package br.com.alura.financask.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.alura.financask.model.User
import br.com.alura.financask.repository.FirebaseAuthRepository
import br.com.alura.financask.repository.Resource

class LoginViewModel(private val firebaseAuthRepository: FirebaseAuthRepository
) : ViewModel() {

    fun isLoged(): Boolean = firebaseAuthRepository.isLoged()

    fun desloga() {
        firebaseAuthRepository.desloga()
    }

    fun naoEstaLogado(): Boolean = !isLoged()

    fun registerUser(user: User): LiveData<Resource<Boolean>> {
        return firebaseAuthRepository.register(user)
    }
}