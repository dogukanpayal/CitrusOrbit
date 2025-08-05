package com.dogukanpayal.developmentproject

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsAnimationCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.doOnLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dogukanpayal.developmentproject.databinding.FragmentForgotYourPasswordBinding

class ForgotYourPasswordFragment : Fragment(R.layout.fragment_forgot_your_password) {

    private var _binding: FragmentForgotYourPasswordBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentForgotYourPasswordBinding.bind(view)


        binding.forgotPasswordGeriTextButton.setOnClickListener {
            findNavController().navigate(
                ForgotYourPasswordFragmentDirections
                    .actionForgotYourPasswordFragmentToLoginScreenFragment()
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
