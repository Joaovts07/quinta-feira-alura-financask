package br.com.alura.financask.repository

import br.com.alura.financask.model.Transaction

interface TransacaoRepository {
    suspend fun insertTransacao(name: String, email: String): Long

    suspend fun updateTransacao(id: Long, name: String, email: String)

    suspend fun deleteTransacao(id: Long)

    suspend fun deleteAllTransacao()

    suspend fun getAllTransacoes(): List<Transaction>
}
