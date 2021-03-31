package br.com.alura.financask.repository

import br.com.alura.financask.dao.TransacaoDAO
import br.com.alura.financask.model.Transaction

class DatabaseDataSource(private val transacaoDAO: TransacaoDAO) : TransacaoRepository {
    override suspend fun insertTransacao(name: String, email: String): Long {
        TODO("Not yet implemented")
    }

    override suspend fun updateTransacao(id: Long, name: String, email: String) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTransacao(id: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllTransacao() {
        TODO("Not yet implemented")
    }

    override suspend fun getAllTransacoes(): List<Transaction> {
        return transacaoDAO.getAll()
    }
}