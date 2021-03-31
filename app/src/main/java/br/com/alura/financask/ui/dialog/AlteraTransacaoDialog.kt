package br.com.alura.financask.ui.dialog

import android.content.Context
import android.view.ViewGroup
import br.com.alura.financask.R
import br.com.alura.financask.extension.formataParaBrasileiro
import br.com.alura.financask.model.Type
import br.com.alura.financask.model.Transaction

class AlteraTransacaoDialog(
        viewGroup: ViewGroup,
        private val context: Context) : FormularioTransacaoDialog(context, viewGroup) {

    override val tituloBotaoPositivo: String
        get() = "Alterar"

    override fun tituloPor(type: Type): Int {
        if (type == Type.RECEITA) {
            return R.string.altera_receita
        }
        return R.string.altera_despesa
    }

    fun chama(transaction: Transaction, delegate: (transaction: Transaction) -> Unit) {
        val tipo = transaction.type
        super.chama(tipo, delegate)
        inicializaCampos(transaction)
    }

    private fun inicializaCampos(transaction: Transaction) {
        val tipo = transaction.type
        inicializaCampoValor(transaction)
        inicializaCampoData(transaction)
        inicializaCampoCategoria(tipo, transaction)
    }

    private fun inicializaCampoCategoria(type: Type, transaction: Transaction) {
        val categoriasRetornadas = context.resources.getStringArray(categoriasPor(type))
        val posicaoCategoria = categoriasRetornadas.indexOf(transaction.categoria)
        campoCategoria.setSelection(posicaoCategoria, true)
    }

    private fun inicializaCampoData(transaction: Transaction) {
        campoData.setText(transaction.data.formataParaBrasileiro())
    }

    private fun inicializaCampoValor(transaction: Transaction) {
        campoValor.setText(transaction.valor.toString())
    }

}