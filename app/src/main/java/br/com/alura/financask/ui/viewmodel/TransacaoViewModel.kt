package br.com.alura.financask.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.alura.financask.model.Transaction
import br.com.alura.financask.repository.TransacaoRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class TransacaoViewModel(private val repository: TransacaoRepository) : ViewModel() {

    val getAllTransactions: LiveData<List<Transaction>> = repository.getAllTransacoes()

    private val _messageEventData = MutableLiveData<Int>()
    val messageEventData: LiveData<Int>
        get() = _messageEventData

    private val _transactionStateEventData = MutableLiveData<TransactionState>()
    val transactionStateEventData: LiveData<TransactionState>
        get() = _transactionStateEventData

    fun addTransaction(transaction: Transaction) = viewModelScope.launch {
        try {
            val id = repository.insertTransacao(transaction)
            if (id > 0) {
                _transactionStateEventData.value = TransactionState.Inserted
                _messageEventData.value = 10
            }
        } catch (ex: Exception) {
            _messageEventData.value = 0
        }
    }

    fun remove(idTransaction: Long) = viewModelScope.launch(IO) {
        repository.deleteTransacao(idTransaction)
    }

    fun updateTransaction(transaction: Transaction) = viewModelScope.launch(IO) {
        repository.updateTransacao(transaction)
    }

    sealed class TransactionState {
        object Inserted : TransactionState()
        object Updated : TransactionState()
    }

    companion object {
        private val TAG = TransacaoViewModel::class.java.simpleName
    }
}


