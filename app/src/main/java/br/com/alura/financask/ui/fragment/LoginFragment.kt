package br.com.alura.financask.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.alura.financask.R

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

}