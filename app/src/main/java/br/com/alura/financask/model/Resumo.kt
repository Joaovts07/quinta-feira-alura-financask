package br.com.alura.financask.model

import java.math.BigDecimal

class Resumo(private val transacoes: List<Transaction>) {

    val receita get() = somaPor(Type.RECEITA)

    val despesa get() = somaPor(Type.DESPESA)

    val total get() = receita.subtract(despesa)

    private fun somaPor(type: Type) : BigDecimal {
        val somaDeTransacoesPeloTipo = transacoes
                .filter { it.type == type }
                .sumByDouble { it.valor.toDouble() }
        return BigDecimal(somaDeTransacoesPeloTipo)
    }
}