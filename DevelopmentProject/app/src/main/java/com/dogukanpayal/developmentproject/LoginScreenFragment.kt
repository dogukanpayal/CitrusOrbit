package com.dogukanpayal.developmentproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.dogukanpayal.developmentproject.databinding.FragmentLoginScreenBinding
import com.dogukanpayal.developmentproject.LoginScreenFragmentDirections



class LoginScreenFragment : Fragment() {

    private var _binding: FragmentLoginScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginScreenBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginScreenBinding.bind(view)
        binding.kayitOlTextButton.setOnClickListener {
            val action = LoginScreenFragmentDirections.actionLoginScreenFragmentToRegisterScreenFragment()
            findNavController().navigate(action)
        }

        binding.yeniSifreTextButton.setOnClickListener {
            val action = LoginScreenFragmentDirections.actionLoginScreenFragmentToForgotYourPasswordFragment()
            findNavController().navigate(action)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}