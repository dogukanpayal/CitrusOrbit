package com.dogukanpayal.developmentproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsAnimationCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.doOnLayout
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.dogukanpayal.developmentproject.databinding.FragmentLoginScreenBinding
import com.google.firebase.database.*

class LoginScreenFragment : Fragment(R.layout.fragment_login_screen) {

    private var _binding: FragmentLoginScreenBinding? = null
    private val binding get() = _binding!!
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databaseReference = FirebaseDatabase.getInstance().reference.child("users")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginScreenBinding.bind(view)


        val formBox   = binding.formFieldBox
        val scrollView = binding.formScrollView

        formBox.doOnLayout {
            val originalY = it.y
            ViewCompat.setOnApplyWindowInsetsListener(formBox) { v, insets ->
                val imeH = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom.toFloat()
                v.translationY = -minOf(imeH, originalY)
                insets
            }
            ViewCompat.setWindowInsetsAnimationCallback(formBox,
                object : WindowInsetsAnimationCompat.Callback(
                    WindowInsetsAnimationCompat.Callback.DISPATCH_MODE_CONTINUE_ON_SUBTREE
                ) {
                    override fun onProgress(
                        insets: WindowInsetsCompat,
                        running: MutableList<WindowInsetsAnimationCompat>
                    ): WindowInsetsCompat {
                        val imeH = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom.toFloat()
                        formBox.translationY = -minOf(imeH, originalY)
                        scrollView.scrollTo(0, binding.girisYapButton.bottom)
                        return insets
                    }
                })
        }


        binding.kayitOlTextButton.setOnClickListener {
            findNavController().navigate(LoginScreenFragmentDirections
                .actionLoginScreenFragmentToRegisterScreenFragment())
        }
        binding.yeniSifreTextButton.setOnClickListener {
            findNavController().navigate(LoginScreenFragmentDirections
                .actionLoginScreenFragmentToForgotYourPasswordFragment())
        }
        binding.girisYapButton.setOnClickListener {
            val email = binding.emailInputBox.text.toString().trim()
            val pw    = binding.passwordInputBox.text.toString().trim()
            if (email.isEmpty() || pw.isEmpty()) {
                Toast.makeText(requireContext(),
                    "Lütfen email ve şifreyi girin.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            loginUser(email, pw)
        }
    }

    private fun loginUser(email: String, password: String) {
        databaseReference.orderByChild("email").equalTo(email)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (!snapshot.exists()) {
                        Toast.makeText(requireContext(),
                            "Böyle bir hesap bulunamadı", Toast.LENGTH_SHORT).show()
                        return
                    }
                    for (child in snapshot.children) {
                        val user = child.getValue(UserData::class.java)
                        if (user?.password == password) {
                            Toast.makeText(requireContext(),
                                "Giriş Başarılı", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(requireContext(),
                                "Şifre hatalı", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(requireContext(),
                        "Veritabanı hatası: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
