package br.com.alura.financask.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.alura.financask.extension.getLastDayOfMonth
import br.com.alura.financask.extension.getTimeStampforDateString
import br.com.alura.financask.model.Transaction
import br.com.alura.financask.repository.TransacaoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class TransacaoViewModel(private val repository: TransacaoRepository) : ViewModel() {

    val transactionsList = MutableLiveData<List<Transaction>>()

    fun getTransactions() {
        CoroutineScope(Dispatchers.Main).launch {
            val transactions = withContext(Dispatchers.Default) {
                repository.getAllTransacoes()
            }
            transactionsList.value = transactions
        }
    }

    fun getTransactionsByFilter(month: String) {

        val myCalendar = Calendar.getInstance()
        val startDate = myCalendar.getTimeStampforDateString("01/$month/2021")
        val lastDay = myCalendar.getLastDayOfMonth(month)
        val endDate = myCalendar.getTimeStampforDateString("$lastDay/$month/2021")

        CoroutineScope(Dispatchers.Main).launch {
            val transactions = withContext(Dispatchers.Default) {
                repository.getAllTransacoesByFilter(startDate, endDate)
            }
            transactionsList.value = transactions
        }
    }

    private val _messageEventData = MutableLiveData<Int>()

    private val _transactionStateEventData = MutableLiveData<TransactionState>()

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


