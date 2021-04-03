package br.com.alura.financask.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.alura.financask.model.Summary
import br.com.alura.financask.model.Transaction
import br.com.alura.financask.repository.TransacaoRepository
import kotlinx.coroutines.launch

class ResumoViewModel(private val repository: TransacaoRepository) : ViewModel() {

    val allTransaction: LiveData<List<Transaction>> = repository.getAllTransacoes()

}