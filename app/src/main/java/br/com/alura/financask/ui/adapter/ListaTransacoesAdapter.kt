package br.com.alura.financask.ui.adapter

import android.content.Context
import androidx.core.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import br.com.alura.financask.R
import br.com.alura.financask.extension.formataParaBrasileiro
import br.com.alura.financask.extension.limitaEmAte
import br.com.alura.financask.model.Type
import br.com.alura.financask.model.Transaction
import kotlinx.android.synthetic.main.transacao_item.view.*

class ListaTransacoesAdapter(private val transacoes: List<Transaction>,
                             private val context: Context) : BaseAdapter(){

    private val limiteDaCategoria = 14

    override fun getView(posicao: Int, view: View?, parent: ViewGroup?): View {
        val viewCriada = LayoutInflater.from(context)
                .inflate(R.layout.transacao_item, parent, false)

        val transacao = transacoes[posicao]

        adicionaValor(transacao, viewCriada)
        adicionaIcone(transacao, viewCriada)
        adicionaCategoria(viewCriada, transacao)
        adicionaData(viewCriada, transacao)

        return viewCriada
    }

    private fun adicionaData(viewCriada: View, transaction: Transaction) {
        viewCriada.transacao_data.text = transaction.data
                .formataParaBrasileiro()
    }

    private fun adicionaCategoria(viewCriada: View, transaction: Transaction) {
        viewCriada.transacao_categoria.text = transaction.categoria
                .limitaEmAte(limiteDaCategoria)
    }

    private fun adicionaIcone(transaction: Transaction, viewCriada: View) {
        val icone = iconePor(transaction.type)
        viewCriada.transacao_icone
                .setBackgroundResource(icone)
    }

    private fun iconePor(type: Type): Int {
        if (type == Type.RECEITA) {
            return R.drawable.icone_transacao_item_receita
        }
        return R.drawable.icone_transacao_item_despesa
    }

    private fun adicionaValor(transaction: Transaction, viewCriada: View) {
        val cor: Int = corPor(transaction.type)
        viewCriada.transacao_valor
                .setTextColor(cor)
        viewCriada.transacao_valor.text = transaction.valor
                .formataParaBrasileiro()
    }

    private fun corPor(type: Type): Int {
        if (type == Type.RECEITA) {
           return ContextCompat.getColor(context, R.color.receita)
        }
         return ContextCompat.getColor(context, R.color.despesa)
    }

    override fun getItem(posicao: Int): Transaction {
        return transacoes[posicao]
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return transacoes.size
    }

}