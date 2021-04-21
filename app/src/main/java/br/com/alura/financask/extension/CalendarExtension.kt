package br.com.alura.financask.extension

import java.text.SimpleDateFormat
import java.util.*

fun Calendar.formataParaBrasileiro() : String{
    val formatoBrasileiro = "dd/MM/yyyy"
    val format = SimpleDateFormat(formatoBrasileiro)
    return format.format(this.time)
}

fun Calendar.getTimeStampforDateString(dateStr: String) : Long {
    val sdf = SimpleDateFormat("dd/MM/yyyy")
    val date: Date = sdf.parse(dateStr)
    return date.time
}

fun Calendar.getLastDayOfMonth(dateStr: String) : Int {
    val sdf = SimpleDateFormat("dd/MM/yyyy")
    this.time =sdf.parse("01/$dateStr/2021")
    return getActualMaximum(Calendar.DAY_OF_MONTH)
}