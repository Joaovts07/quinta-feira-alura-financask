package br.com.alura.financask.ui.fragment

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.financask.R
import br.com.alura.financask.model.Transaction
import br.com.alura.financask.model.Type
import br.com.alura.financask.ui.ResumoView
import br.com.alura.financask.ui.dialog.AdicionaTransacaoDialog
import br.com.alura.financask.ui.dialog.AlteraTransacaoDialog
import br.com.alura.financask.ui.recyclerview.adapter.ListTransactionsAdapter
import br.com.alura.financask.ui.viewmodel.LoginViewModel
import br.com.alura.financask.ui.viewmodel.TransacaoViewModel
import kotlinx.android.synthetic.main.fragment_lista_transacoes.*
import org.koin.android.viewmodel.ext.android.viewModel


class ListaTransacoesFragment : Fragment(R.layout.fragment_lista_transacoes) {

    private lateinit var transactions: List<Transaction>
    private val viewDaActivity by lazy {
        activity?.window?.decorView
    }
    private val viewGroupDaActivity by lazy {
        viewDaActivity as ViewGroup
    }

    private val viewModel: TransacaoViewModel by viewModel()

    private val loginViewModel: LoginViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        isLoged()
        configuraLista()
        configuraFab()
        registerForContextMenu(view)
    }

    private fun isLoged() {
        if (loginViewModel.naoEstaLogado()) {
            gotToLogin()
        }
    }

    private fun gotToLogin() {
        findNavController().navigate(R.id.action_listaTransacoesFragment_to_loginFragment)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_principal, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_principal_deslogar) {
            loginViewModel.desloga()
            gotToLogin()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun remove(posicaoDaTransacao: Long) {
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
                with(rv_list_transactions) {
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(
                            context,
                            RecyclerView.VERTICAL, false
                    )
                    setHasFixedSize(true)
                    adapter = ListTransactionsAdapter(
                            transactions,
                            this@ListaTransacoesFragment::chamaDialogDeAlteracao) {
                        remove(it.id)
                    }
                }
                val resumoView = context?.let {
                    view?.let { it1 -> ResumoView(it, it1, transactions) }
                }
                resumoView?.atualiza()
            }
        })
    }

    private fun chamaDialogDeAlteracao(transaction: Transaction) {
        context?.let {
            AlteraTransacaoDialog(viewGroupDaActivity, it)
                    .chama(transaction) { transacaoAlterada ->
                        altera(transacaoAlterada.changeId(transaction.id))
                    }
        }
    }

    private fun altera(transaction: Transaction) {
        viewModel.updateTransaction(transaction)
    }
}