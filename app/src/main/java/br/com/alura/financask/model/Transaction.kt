package br.com.alura.financask.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.util.*

@Entity(tableName = "transaction")
class Transaction(
        @PrimaryKey(autoGenerate = true)
        val id: Long = 0,
        val valor: BigDecimal,
        val categoria: String = "Indefinida",
        val type: Type,
        val data: Calendar = Calendar.getInstance())