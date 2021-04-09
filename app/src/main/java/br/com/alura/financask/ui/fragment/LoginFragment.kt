package br.com.alura.financask.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.alura.financask.R
import kotlinx.android.synthetic.main.login.*

class LoginFragment : Fragment() {

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
        goToRegisterForm()
    }

    private fun goToRegisterForm() {
        login_botao_cadastrar_usuario.setOnClickListener {
            val navigation = findNavController()
            navigation.navigate(R.id.action_loginFragment_to_cadastroFragment)
        }
    }

}