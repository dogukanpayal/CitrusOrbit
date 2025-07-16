package com.dogukanpayal.developmentproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.dogukanpayal.developmentproject.databinding.FragmentForgotYourPasswordBinding
import com.dogukanpayal.developmentproject.databinding.FragmentLoginScreenBinding

class ForgotYourPasswordFragment : Fragment() {

    private var _binding: FragmentForgotYourPasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentForgotYourPasswordBinding.inflate(inflater, container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding=FragmentForgotYourPasswordBinding.bind(view)
        binding.forgotPasswordGeriTextButton.setOnClickListener {
            val action = ForgotYourPasswordFragmentDirections.actionForgotYourPasswordFragmentToLoginScreenFragment()
            findNavController().navigate(action)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding
    }





}