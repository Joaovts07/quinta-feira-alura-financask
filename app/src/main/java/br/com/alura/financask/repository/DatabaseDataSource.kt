package br.com.alura.financask.repository

import androidx.lifecycle.LiveData
import br.com.alura.financask.dao.TransacaoDAO
import br.com.alura.financask.model.Transaction

class DatabaseDataSource(private val transacaoDAO: TransacaoDAO) : TransacaoRepository {

    override suspend fun insertTransacao(transaction: Transaction): Long {
        return transacaoDAO.insert(transaction)
    }

    override suspend fun updateTransacao(transaction: Transaction) {
        transacaoDAO.update(transaction)
    }

    override suspend fun deleteTransacao(id: Long) {
        transacaoDAO.delete(id)
    }

    override suspend fun deleteAllTransacao() {
        TODO("Not yet implemented")
    }

    override fun getAllTransacoes(): LiveData<List<Transaction>> {
        return  transacaoDAO.getAll()
    }


}