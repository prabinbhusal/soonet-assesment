package com.assessment.soonet.ui.login

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.assessment.soonet.R
import com.assessment.soonet.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private var auth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonLogin.setOnClickListener {
            loginWithEmailPassword()
        }

        binding.buttonSignUp.setOnClickListener {
            navigateToSignUpFragment()
        }
    }

    private fun loginWithEmailPassword() {
        val email = binding.editTextEmail.text.toString().trim()
        val password = binding.editTextPassword.text.toString().trim()

        if (TextUtils.isEmpty(email)) {
            binding.editTextEmail.error = "Enter your email Address!!"
            return
        }
        if (TextUtils.isEmpty(password)) {
            binding.editTextPassword.error = "Enter your Password"
            return
        }
        if (password.length < 6) {
            binding.editTextPassword.error = "Password too short"
            return
        }

        binding.progress.visibility = View.VISIBLE

        auth!!.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("AUTH", "signInWithEmail:success")
                    Toast.makeText(
                        activity, "Login Successful",
                        Toast.LENGTH_SHORT).show()
                    binding.progress.visibility = View.GONE
                    navigateToDashBoardFragment()

                } else {
                    binding.progress.visibility = View.GONE
                    // If sign in fails, display a message to the user.
                    Log.w("AUTH", "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        activity, "${task.exception!!.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

    }

    private fun navigateToSignUpFragment() {
        findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
    }

    private fun navigateToDashBoardFragment() {
        findNavController().navigate(R.id.action_loginFragment_to_navigation_dashboard)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}