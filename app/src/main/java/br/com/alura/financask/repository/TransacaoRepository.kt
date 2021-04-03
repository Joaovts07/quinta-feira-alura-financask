package br.com.alura.financask.repository

import androidx.lifecycle.LiveData
import br.com.alura.financask.model.Transaction

interface TransacaoRepository {
    suspend fun insertTransacao(transaction: Transaction): Long

    suspend fun updateTransacao(transaction: Transaction)

    suspend fun deleteTransacao(id: Long)

    suspend fun deleteAllTransacao()

    fun getAllTransacoes(): LiveData<List<Transaction>>
}
