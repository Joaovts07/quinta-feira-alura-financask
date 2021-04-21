package br.com.alura.financask.repository

import br.com.alura.financask.model.Transaction

interface TransacaoRepository {
    suspend fun insertTransacao(transaction: Transaction): Long

    suspend fun updateTransacao(transaction: Transaction)

    suspend fun deleteTransacao(id: Long)

    suspend fun deleteAllTransacao()

    suspend fun getAllTransacoes(): List<Transaction>

    suspend fun getAllTransacoesByFilter(startDate: Long, endDate: Long): List<Transaction>

}
