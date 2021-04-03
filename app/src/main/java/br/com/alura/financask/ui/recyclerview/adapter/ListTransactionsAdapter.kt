package br.com.alura.financask.ui.recyclerview.adapter

import android.view.*
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.financask.R
import br.com.alura.financask.extension.formataParaBrasileiro
import br.com.alura.financask.model.Transaction
import br.com.alura.financask.model.Type
import kotlinx.android.synthetic.main.transacao_item.view.*

class ListTransactionsAdapter(private val transactions: List<Transaction>,
                              private val onItemClickListener: (transaction: Transaction) -> Unit)
    : RecyclerView.Adapter<ListTransactionsAdapter.TransactionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.transacao_item, parent, false)
        return TransactionViewHolder(view, onItemClickListener)
    }


    override fun getItemCount(): Int = transactions.size

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bindView(transactions[position])
    }

    class TransactionViewHolder(
            var view: View,
            private val onItemClickListener: (transaction: Transaction) -> Unit
    ) : RecyclerView.ViewHolder(view.rootView),
            View.OnCreateContextMenuListener {
        private val category = view.transacao_categoria
        private val transactionDate = view.transacao_data
        private val transactionValue = view.transacao_valor


        fun bindView(transaction: Transaction) {
            category.text = transaction.categoria
            transactionDate.text = transaction.data.formataParaBrasileiro()
            transactionValue.text = transaction.valor.formataParaBrasileiro()
            adicionaIcone(transaction, view)
            view.setOnClickListener {
                onItemClickListener.invoke(transaction)
            }

        }

        private fun adicionaIcone(transaction: Transaction, view: View) {
            val icone = iconePor(transaction.type)
            view.transacao_icone
                    .setBackgroundResource(icone)
        }

        private fun iconePor(type: Type): Int {
            if (type == Type.RECEITA) {
                return R.drawable.icone_transacao_item_receita
            }
            return R.drawable.icone_transacao_item_despesa
        }

        override fun onCreateContextMenu(menu: ContextMenu?, p1: View?, p2: ContextMenu.ContextMenuInfo?) {
            menu?.let {
                it.add(Menu.NONE, 1, Menu.NONE, "Remover")
            }
        }
    }
}
