package com.assessment.soonet.ui.signup

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
import com.assessment.soonet.databinding.FragmentSignupBinding
import com.google.firebase.auth.FirebaseAuth

class SignupFragment : Fragment() {

    private var _binding: FragmentSignupBinding? = null
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

        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSignUp.setOnClickListener {
            signupWithEmailPassword()
        }

        binding.buttonLogin.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun signupWithEmailPassword() {
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

        auth!!.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    binding.progress.visibility = View.GONE
                    Log.d("AUTH", "createUserWithEmail:success")
                    Toast.makeText(
                        activity, "Signup Successful",
                        Toast.LENGTH_SHORT).show()
                    navigateToDashBoardFragment()
                } else {
                    binding.progress.visibility = View.GONE
                    Log.w("AUTH", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(activity, "${task.exception!!.message}", Toast.LENGTH_SHORT).show()
                }
            }

    }

    private fun navigateToDashBoardFragment() {
        findNavController().navigate(R.id.action_signupFragment_to_navigation_dashboard)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}