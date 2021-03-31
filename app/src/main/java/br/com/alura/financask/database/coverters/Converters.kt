package br.com.alura.financask.database.coverters

import androidx.room.TypeConverter
import br.com.alura.financask.model.Type
import java.math.BigDecimal
import java.util.*

class Converters {
    @TypeConverter
    fun fromLong(value: Long?): BigDecimal? {
        return value?.let { BigDecimal(it) }
    }

    @TypeConverter
    fun toBigDecimal(bigDecimal: BigDecimal?): Long? {
        return bigDecimal?.toLong()
    }

    @TypeConverter
    fun fromCalendarLong(valor: Calendar?): Long? {
        return valor?.timeInMillis
    }

    @TypeConverter
    fun toCalendar(valor: Long?): Calendar? {
        val now = Calendar.getInstance()
        if (valor != null) {
            now.timeInMillis = valor
        }
        return now
    }

    @TypeConverter
    fun fromIntType(value: Type?): Int? {
        if (value == Type.RECEITA) {
            return 1
        }
        return 2
    }

    @TypeConverter
    fun toType(value: Int?): Type? {
        if(value == 1){
            return Type.RECEITA
        }
        return Type.DESPESA
    }


}