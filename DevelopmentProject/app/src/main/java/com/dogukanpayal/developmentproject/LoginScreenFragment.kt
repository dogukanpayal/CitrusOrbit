package com.dogukanpayal.developmentproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.dogukanpayal.developmentproject.databinding.FragmentLoginScreenBinding
import com.dogukanpayal.developmentproject.LoginScreenFragmentDirections
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class LoginScreenFragment : Fragment() {

    private var _binding: FragmentLoginScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("users")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginScreenBinding.inflate(inflater, container, false)
        return binding.root
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

        binding.girisYapButton.setOnClickListener {
            val email = binding.emailInputBox.text.toString().trim()
            val password = binding.passwordInputBox.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()){
                Toast.makeText(requireContext(),"Lütfen email ve şifreyi girin.",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            loginUser(email,password)

        }
    }

    private fun loginUser(email: String, password: String){
        databaseReference.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(!snapshot.exists()){
                    Toast.makeText(requireContext(),"Böyle bir hesap bulunamadı",Toast.LENGTH_SHORT).show()
                    return
                }

                for (child in snapshot.children){
                    val user = child.getValue(UserData::class.java)
                    if(user?.password == password){
                        Toast.makeText(requireContext(),"Giriş Başarılı",Toast.LENGTH_SHORT).show()

                    }
                    else{
                        Toast.makeText(requireContext(),"Şifre hatalı",Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(),"Veritabanı hatası: ${error.message}",Toast.LENGTH_SHORT).show()
            }
        })
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}