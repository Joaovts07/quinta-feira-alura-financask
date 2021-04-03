package br.com.alura.financask.ui.fragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.alura.financask.R
import br.com.alura.financask.database.AppDatabase
import br.com.alura.financask.model.Transaction
import br.com.alura.financask.model.Type
import br.com.alura.financask.repository.DatabaseDataSource
import br.com.alura.financask.repository.TransacaoRepository
import br.com.alura.financask.ui.ResumoView
import br.com.alura.financask.ui.adapter.ListaTransacoesAdapter
import br.com.alura.financask.ui.dialog.AdicionaTransacaoDialog
import br.com.alura.financask.ui.dialog.AlteraTransacaoDialog
import br.com.alura.financask.ui.viewmodel.TransacaoViewModel
import kotlinx.android.synthetic.main.fragment_lista_transacoes.*


class ListaTransacoesFragment : Fragment(R.layout.fragment_lista_transacoes) {

    //private val dao = TransacaoDAO()
    //private val transacoes = dao.transacoes
    private lateinit var transactions: List<Transaction>
    private val viewDaActivity by lazy {
        activity?.let {
            it.window.decorView
        }
    }
    private val viewGroupDaActivity by lazy {
        viewDaActivity as ViewGroup
    }

    private val viewModel: TransacaoViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val subscriberDao = AppDatabase.getInstance(requireContext()).transactionDAO
                val repository: TransacaoRepository = DatabaseDataSource(subscriberDao)
                return TransacaoViewModel(repository) as T
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configuraLista()
        configuraFab()
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val idDoMenu = item.itemId
        if (idDoMenu == 1) {
            val adapterMenuInfo = item.menuInfo as AdapterView.AdapterContextMenuInfo
            val posicaoDaTransacao = adapterMenuInfo.position
            remove(transactions[posicaoDaTransacao].id)
        }
        return super.onContextItemSelected(item)

    }

    private fun remove(posicaoDaTransacao: Long) {
        //dao.remove(posicaoDaTransacao)
        viewModel.remove(posicaoDaTransacao)

    }

    private fun configuraFab() {
        lista_transacoes_adiciona_receita
                .setOnClickListener {
                    chamaDialogDeAdicao(Type.RECEITA)
                }
        lista_transacoes_adiciona_despesa
                .setOnClickListener {
                    chamaDialogDeAdicao(Type.DESPESA)
                }
    }

    private fun adiciona(transaction: Transaction) {
        //dao.adiciona(transaction)
        viewModel.addTransaction(transaction)

    }

    private fun chamaDialogDeAdicao(type: Type) {
        context?.let {
            AdicionaTransacaoDialog(viewGroupDaActivity, it)
                    .chama(type) { transacaoCriada ->
                        adiciona(transacaoCriada)
                        lista_transacoes_adiciona_menu.close(true)
                    }
        }

    }

    private fun configuraLista() {

        viewModel.getAllTransactions.observe(viewLifecycleOwner, Observer {
            it?.let { transactions ->
                this.transactions = transactions
                val listTransactionsAdapter = context?.let {
                    ListaTransacoesAdapter(transactions, it)
                }
                with(lista_transacoes_listview) {
                    adapter = listTransactionsAdapter
                    setOnItemClickListener { _, _, posicao, _ ->
                        val transaction = transactions[posicao]
                        chamaDialogDeAlteracao(transaction, posicao)
                    }
                    setOnCreateContextMenuListener { menu, _, _ ->
                        menu.add(Menu.NONE, 1, Menu.NONE, "Remover")
                    }
                }
                val resumoView = context?.let { view?.let { it1 -> ResumoView(it, it1, transactions) } }
                resumoView?.atualiza()
            }
        })
        /*val listaTransacoesAdapter = context?.let { ListaTransacoesAdapter(transacoes, it) }
        with(lista_transacoes_listview) {
            adapter = listaTransacoesAdapter
            setOnItemClickListener { _, _, posicao, _ ->
                val transacao = transacoes[posicao]
                chamaDialogDeAlteracao(transacao, posicao)
            }
            setOnCreateContextMenuListener { menu, _, _ ->
                menu.add(Menu.NONE, 1, Menu.NONE, "Remover")
            }
        }*/
    }

    private fun chamaDialogDeAlteracao(transaction: Transaction, posicao: Int) {
        context?.let {
            AlteraTransacaoDialog(viewGroupDaActivity, it)
                    .chama(transaction) { transacaoAlterada ->
                        altera(transacaoAlterada, posicao)
                    }
        }
    }

    private fun altera(transaction: Transaction, posicao: Int) {
        //dao.altera(transaction, posicao)

    }

}