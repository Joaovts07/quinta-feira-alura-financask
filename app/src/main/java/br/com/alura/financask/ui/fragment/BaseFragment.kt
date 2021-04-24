package br.com.alura.financask.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.alura.financask.R
import br.com.alura.financask.ui.viewmodel.LoginViewModel
import org.koin.android.viewmodel.ext.android.viewModel


open class BaseFragment : Fragment() {

    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isLoged()
    }

    private fun isLoged() {
        if (loginViewModel.naoEstaLogado()) {
            gotToLogin()
        }
    }

    protected fun gotToLogin() {
        findNavController().navigate(R.id.action_global_goTo_login)
    }
}