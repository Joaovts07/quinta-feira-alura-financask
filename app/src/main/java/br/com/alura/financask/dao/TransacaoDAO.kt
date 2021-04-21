package br.com.alura.financask.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import br.com.alura.financask.model.Transaction
import java.util.*

@Dao
interface TransacaoDAO {
    @Insert
    suspend fun insert(transaction: Transaction): Long

    @Update
    suspend fun update(transaction: Transaction )

    @Query("SELECT * FROM `transaction`")
    suspend fun getAll(): List<Transaction>

    @Query("DELETE FROM `transaction` WHERE id = :id")
    fun delete(id:Long)
    @Query("SELECT * FROM `transaction` WHERE data BETWEEN :startDate AND :endDate")
    fun getTransactions(startDate: Long,endDate: Long): List<Transaction>
}