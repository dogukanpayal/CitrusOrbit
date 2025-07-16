package com.dogukanpayal.developmentproject

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.dogukanpayal.developmentproject.databinding.FragmentLoginScreenBinding
import com.dogukanpayal.developmentproject.databinding.FragmentRegisterScreenBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsAnimationCompat

class RegisterScreenFragment : Fragment() {

    private var _binding: FragmentRegisterScreenBinding? = null
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
        _binding = FragmentRegisterScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRegisterScreenBinding.bind(view)

        val whiteBox = binding.registerFormFieldBox

        ViewCompat.setOnApplyWindowInsetsListener(whiteBox){v,insets ->
            val imeHeight = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
            v.translationY = -imeHeight.toFloat()
            insets
        }

        ViewCompat.setWindowInsetsAnimationCallback(whiteBox,
            object : WindowInsetsAnimationCompat.Callback(
                WindowInsetsAnimationCompat.Callback.DISPATCH_MODE_CONTINUE_ON_SUBTREE
            ){

                override fun onProgress(
                    insets: WindowInsetsCompat,
                    runningAnimations: MutableList<WindowInsetsAnimationCompat>
                ): WindowInsetsCompat {
                    val imeHeight = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
                    whiteBox.translationY = -imeHeight.toFloat()
                    return insets
                }
            }

        )








        binding.kayitOlButton.isEnabled = binding.KVKKCheckBox.isChecked

        binding.KVKKCheckBox.setOnCheckedChangeListener { _, isChecked ->
            binding.kayitOlButton.isEnabled = isChecked
        }

        binding.kayitOlButton.setOnClickListener {

            val username = binding.kullaniciAdiInputEditText.text.toString().trim()
            val email = binding.registerEmailEditText.text.toString().trim()
            val phone = binding.telefonNumarasiInputEditText.text.toString().trim()
            val password = binding.passwordInputEditText.text.toString().trim()
            val confirmPassword = binding.passwordAgainInputEditText.text.toString().trim()

            if (username.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()){
                Toast.makeText(requireContext(),"Lütfen tüm alanları doldurun", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(password != confirmPassword){
                Toast.makeText(requireContext(),"Şifreler uyuşmuyor",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!binding.KVKKCheckBox.isChecked){
                Toast.makeText(requireContext(),"KVKK metnini onaylayın",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            signupUser(username,email,phone,password)
        }


        binding.girisYapTextButton.setOnClickListener {
            val action = RegisterScreenFragmentDirections.actionRegisterScreenFragmentToLoginScreenFragment()
            findNavController().navigate(action)
        }
    }

    private fun signupUser(username: String, email: String, password:String, phone: String){

        databaseReference.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (!dataSnapshot.exists()){
                    val id = databaseReference.push().key
                    val userData = UserData(id,username,email,phone,password)
                    databaseReference.child(id!!).setValue(userData)
                    Toast.makeText(requireContext(),"Signup Successful", Toast.LENGTH_SHORT).show()
                    findNavController().navigate( RegisterScreenFragmentDirections.actionRegisterScreenFragmentToLoginScreenFragment())
                }
                else{
                    Toast.makeText(requireContext(),"User already exists",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(requireContext(),"Database Error: ${databaseError.message}",Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}