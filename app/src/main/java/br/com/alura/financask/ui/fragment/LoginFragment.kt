package br.com.alura.financask.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import br.com.alura.financask.R
import br.com.alura.financask.model.User
import br.com.alura.financask.ui.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.login.*
import org.koin.android.viewmodel.ext.android.viewModel


class LoginFragment : Fragment() {
    private val loginViewModel: LoginViewModel by viewModel()
    private val navController by lazy {
        findNavController()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?)
            : View? {
        return inflater.inflate(
                R.layout.login,
                container,
                false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setButtons()
    }

    private fun authenticateUser() {
        limpaCampos()

        val email = login_usuario_email.editText?.text.toString()
        val senha = login_senha.editText?.text.toString()

        if (validaCampos(email, senha)) {
            loginViewModel.authenticate(User(email, senha)).observe(
                    viewLifecycleOwner, Observer {
                it?.let { recurso ->
                    if (recurso.dado) {
                        goToTransactionsList()
                    } else {
                        val mensagemErro = recurso.erro ?: "Erro durante a autenticação"
                        Toast.makeText(context, mensagemErro, Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }

    private fun goToTransactionsList() {
        navController.navigate(R.id.action_loginFragment_to_listaTransacoesFragment)
    }

    private fun validaCampos(email: String, senha: String): Boolean {
        var valido = true

        if (email.isBlank()) {
            login_usuario_email.error = "E-mail é obrigatório"
            valido = false
        }

        if (senha.isBlank()) {
            login_senha.error = "Senha é obrigatória"
            valido = false
        }
        return valido
    }

    private fun limpaCampos() {
        login_usuario_email.error = null
        login_senha.error = null
    }

    private fun setButtons() {
        login_botao_cadastrar_usuario.setOnClickListener {
            goToRegisterForm()
        }
        login_botao_logar.setOnClickListener {
            authenticateUser()
        }
    }

    private fun goToRegisterForm() {
        navController.navigate(R.id.action_loginFragment_to_cadastroFragment)
    }

}