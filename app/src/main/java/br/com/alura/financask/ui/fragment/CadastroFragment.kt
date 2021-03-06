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
import kotlinx.android.synthetic.main.cadastro_usuario.*
import org.koin.android.viewmodel.ext.android.viewModel

class CadastroFragment : Fragment() {

    private val controlador by lazy {
        findNavController()
    }

    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(
                R.layout.cadastro_usuario,
                container,
                false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setButtonRegister()
    }

    private fun setButtonRegister() {
        cadastro_usuario_botao_cadastrar.setOnClickListener {
            limpaTodosCampos()

            val email = cadastro_usuario_email_usuario.editText?.text.toString().trim()
            val senha = cadastro_usuario_senha.editText?.text.toString()
            val confirmaSenha = cadastro_usuario_confirma_senha.editText?.text.toString()

            val valido = validaCampos(email, senha, confirmaSenha)

            if (valido) {
                registerUser(email, senha)
            }

        }
    }

    private fun validaCampos(
            email: String,
            senha: String,
            confirmaSenha: String
    ): Boolean {
        var valido = true

        if (email.isBlank()) {
            cadastro_usuario_email_usuario.error = "E-mail é necessário"
            valido = false
        }

        else if (senha.isBlank()) {
            cadastro_usuario_senha.error = "Senha é necessária"
            valido = false
        }

        else if (senha != confirmaSenha) {
            cadastro_usuario_confirma_senha.error = "Senhas diferentes"
            valido = false
        }
        return valido
    }

    private fun limpaTodosCampos() {
        cadastro_usuario_email_usuario.error = null
        cadastro_usuario_senha.error = null
        cadastro_usuario_confirma_senha.error = null
    }

    private fun registerUser(email: String, password: String) {

        loginViewModel.registerUser(User(email, password)).observe(viewLifecycleOwner, Observer {
            it?.let { recurso ->
                if (recurso.dado) {
                    Toast.makeText(context, "Cadastro realizado com sucesso", Toast.LENGTH_SHORT).show()
                    controlador.popBackStack()
                } else {
                    val mensagemErro = recurso.erro ?: "Ocorreu uma falha no cadastro"
                    Toast.makeText(context, mensagemErro, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

}