package br.com.alura.financask.ui.recyclerview.adapter

import android.view.*
import android.view.ContextMenu.ContextMenuInfo
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.financask.R
import br.com.alura.financask.extension.formataParaBrasileiro
import br.com.alura.financask.model.Transaction
import br.com.alura.financask.model.Type
import kotlinx.android.synthetic.main.transacao_item.view.*

class ListTransactionsAdapter(private val transactions: List<Transaction>,
                              private val onItemClickListener: (transaction: Transaction) -> Unit)
    : RecyclerView.Adapter<ListTransactionsAdapter.TransactionViewHolder>() {

    private lateinit var onItemClickRemoveContextMenuListener  : OnItemClickRemoveContextMenuListener

    fun setOnItemClickRemoveContextMenuListener(onItemClickRemoveContextMenuListener: OnItemClickRemoveContextMenuListener) {
        this.onItemClickRemoveContextMenuListener = onItemClickRemoveContextMenuListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.transacao_item, parent, false)
        return TransactionViewHolder(view)
    }

    override fun getItemCount(): Int = transactions.size

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bindView(transactions[position])
    }

    inner class TransactionViewHolder(
            var view: View

    ) : RecyclerView.ViewHolder(view.rootView),
            View.OnCreateContextMenuListener {
        private val category = view.transacao_categoria
        private val transactionDate = view.transacao_data
        private val transactionValue = view.transacao_valor


        fun configuraMenuDeContexto(itemView: View, transaction: Transaction) {
            itemView.setOnCreateContextMenuListener { menu: ContextMenu, v: View?, menuInfo: ContextMenuInfo? ->
                MenuInflater(itemView.context).inflate(R.menu.remove_transaction, menu)
                menu.findItem(R.id.menu_lista_produtos_remove)
                        .setOnMenuItemClickListener { item: MenuItem? ->
                            val posicaoProduto = adapterPosition
                            onItemClickRemoveContextMenuListener
                                    .onItemClick(posicaoProduto, transaction)
                            true
                        }
            }
        }

        fun bindView(transaction: Transaction) {
            category.text = transaction.categoria
            transactionDate.text = transaction.data.formataParaBrasileiro()
            transactionValue.text = transaction.valor.formataParaBrasileiro()
            adicionaIcone(transaction, view)
            view.setOnClickListener {
                onItemClickListener.invoke(transaction)
            }
            configuraMenuDeContexto(itemView, transaction)

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

    interface OnItemClickRemoveContextMenuListener {
        fun onItemClick(posicao: Int, produtoRemovido: Transaction)
    }
}
