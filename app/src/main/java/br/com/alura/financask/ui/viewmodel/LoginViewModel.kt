package br.com.alura.financask.ui.viewmodel

import androidx.lifecycle.ViewModel
import br.com.alura.financask.repository.FirebaseAuthRepository

class LoginViewModel(private val firebaseAuthRepository: FirebaseAuthRepository
) : ViewModel() {

    fun isLoged(): Boolean = firebaseAuthRepository.isLoged()

    fun desloga() {
        firebaseAuthRepository.desloga()
    }

    fun naoEstaLogado(): Boolean = !isLoged()

}