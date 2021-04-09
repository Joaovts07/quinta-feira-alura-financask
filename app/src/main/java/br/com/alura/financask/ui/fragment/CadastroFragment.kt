package br.com.alura.financask.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.alura.financask.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.cadastro_usuario.*

class CadastroFragment : Fragment() {

    private val controlador by lazy {
        findNavController()
    }

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
            registerUser()
        }
    }

    private fun limpaTodosCampos() {
        cadastro_usuario_email_usuario.error = null
        cadastro_usuario_senha.error = null
        cadastro_usuario_confirma_senha.error = null
    }

    private fun registerUser() {
        val email = cadastro_usuario_email_usuario.editText?.text.toString().trim()
        val password = cadastro_usuario_senha.editText?.text.toString().trim()
        val instance = FirebaseAuth.getInstance()
        instance.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                controlador.navigate(R.id.action_cadastroFragment_to_listaTransacoesFragment)

            } else {
                // If sign in fails, display a message to the user.
                Log.w("TAG", "createUserWithEmail:failure", task.exception)
                Toast.makeText(context, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()

            }
        }
    }
}