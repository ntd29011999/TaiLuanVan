package com.example.appgrouplocate.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.appgrouplocate.R
import com.example.appgrouplocate.database.auth
import com.example.appgrouplocate.databinding.ActivityLoginBinding
import com.example.appgrouplocate.home.HomeActivity
import com.example.appgrouplocate.main.MainActivity
import com.example.appgrouplocate.signup.SignUpActivity


class LoginActivity : Fragment() {

    private val TAG : String = LoginActivity::class.java.simpleName

    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding = ActivityLoginBinding.inflate(inflater, container, false)



        Log.i(TAG,"Loaded login")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (auth.currentUser!=null) {
            //startActivity(Intent(activity,HomeActivity::class.java))
        }
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            inputLoginInfo = viewModel.loginInfo
            loginBtn.setOnClickListener {
                viewModel.loginInfo = inputLoginInfo!!
                logIn(viewModel.loginInfo)
            }
            toSignUpBtn.setOnClickListener{
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentsContainer_Fl,SignUpActivity())
                    .commit()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.loginInfo = binding.inputLoginInfo!!
    }

    private fun logIn(acc : LoginInfo){
        binding.waitForLoginPb.visibility = View.VISIBLE
        auth.signInWithEmailAndPassword(acc.email.trim(), acc.pass.trim())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    binding.waitForLoginPb.visibility = View.GONE
                    Log.d(TAG, "Successfully signed in with email and password!")
                    // You can access the new user via result.getUser()
                    // Additional user info profile *not* available via:
                    // result.getAdditionalUserInfo().getProfile() == null
                    // You can check if the user is new or existing:
                    // result.getAdditionalUserInfo().isNewUser()
                    if(auth.currentUser==null)
                        Log.d(TAG, "null user after login")
                    (activity as MainActivity).loadFragment(HomeActivity())
                } else {
                    Log.e(TAG, "Error signing in with email and password", task.exception)
                    Toast.makeText(activity, "Error signing in with email and password\n${task.exception}",Toast.LENGTH_LONG).show()
                    binding.waitForLoginPb.visibility = View.GONE
                }
            }
    }

}