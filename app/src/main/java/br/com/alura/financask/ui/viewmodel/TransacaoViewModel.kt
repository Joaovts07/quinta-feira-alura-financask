package br.com.alura.financask.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.alura.financask.model.Transaction
import br.com.alura.financask.repository.TransacaoRepository
import kotlinx.coroutines.launch

class TransacaoViewModel(private val repository: TransacaoRepository) : ViewModel() {

    private val _allTransactionEvent = MutableLiveData<List<Transaction>>()
    val allTransactionEvent: LiveData<List<Transaction>>
        get() = _allTransactionEvent

    fun getSubscribers() = viewModelScope.launch {
        _allTransactionEvent.postValue(repository.getAllTransacoes())
    }
}