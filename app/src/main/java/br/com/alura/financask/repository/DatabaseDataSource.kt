package br.com.alura.financask.repository

import androidx.lifecycle.LiveData
import br.com.alura.financask.dao.TransacaoDAO
import br.com.alura.financask.model.Transaction

class DatabaseDataSource(private val transacaoDAO: TransacaoDAO) : TransacaoRepository {

    override suspend fun insertTransacao(transaction: Transaction): Long {
        return transacaoDAO.insert(transaction)
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

    override fun getAllTransacoes(): LiveData<List<Transaction>> {
        return  transacaoDAO.getAll()
    }


}