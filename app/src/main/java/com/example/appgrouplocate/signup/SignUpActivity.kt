package com.example.appgrouplocate.signup

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.appgrouplocate.database.auth
import com.example.appgrouplocate.database.database
import com.example.appgrouplocate.databinding.ActivitySignUpBinding
import com.example.appgrouplocate.user.User

class SignUpActivity : Fragment() {

    val TAG = SignUpActivity::class.java.simpleName

    private lateinit var viewModel: SignUpViewModel
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            inputSignUpInfo = viewModel.signUpInfo
            toLoginBtn.setOnClickListener {
                //finish()
            }

            signUpBtn.setOnClickListener {
                viewModel.signUpInfo = binding.inputSignUpInfo!!

                signUp(viewModel.signUpInfo)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.signUpInfo = binding.inputSignUpInfo!!
    }

    private fun signUp(acc : User) {
        auth.createUserWithEmailAndPassword(acc.loginEmail, acc.loginPassword)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(activity, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnSuccessListener {
                Toast.makeText(activity,"createUserWithEmail:success",Toast.LENGTH_SHORT).show()
                database.child("users").child(auth.currentUser!!.uid).setValue(acc)
                //finish()
            }
            .addOnFailureListener {
                Toast.makeText(activity,"failed + ${it.message}",Toast.LENGTH_SHORT).show()
            }
    }
}