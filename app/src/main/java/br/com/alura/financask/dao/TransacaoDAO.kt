package br.com.alura.financask.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import br.com.alura.financask.model.Transaction

@Dao
interface TransacaoDAO {
    @Insert
    suspend fun insert(transaction: Transaction): Long

    @Update
    suspend fun update(transaction: Transaction )

    @Query("SELECT * FROM `transaction`")
    fun getAll(): LiveData<List<Transaction>>
}