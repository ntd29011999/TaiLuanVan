package com.example.appgrouplocate.home

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.appgrouplocate.R
import com.example.appgrouplocate.database.auth
import com.example.appgrouplocate.database.database
import com.example.appgrouplocate.databinding.ActivityHomeBinding
import com.example.appgrouplocate.login.LoginActivity
import com.example.appgrouplocate.main.MainActivity
import com.example.appgrouplocate.map.MapsActivity
import com.example.appgrouplocate.profile.ProfileEditActivity
import com.google.android.gms.maps.model.LatLng
import com.squareup.picasso.Picasso

class HomeActivity : Fragment() {
    private val TAG = HomeActivity::class.java.simpleName

    private lateinit var binding : ActivityHomeBinding
    private lateinit var viewModel : HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ActivityHomeBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        (activity as MainActivity).parseData()
        viewModel.dataShow.value = (activity as MainActivity).loggedInAccount
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //get user's profile from database
        database.child("users").child(auth.currentUser!!.uid).get()
            .addOnSuccessListener {
                binding.apply {
                    account = viewModel.dataShow.value
                    Picasso.get().load(viewModel.dataShow.value?.avtUrl).into(avatarIv)
                    toMapsBt.setOnClickListener {
                        startActivity(Intent(activity, MapsActivity::class.java))
                    }
                    newGroupBt.setOnClickListener {
                        dialogNewGroup()
                    }
                }
                binding.waitForDataPb.visibility = View.GONE
            }
            .addOnFailureListener {
                Log.i(TAG,"failed on getting object: ${it.message}")
                Toast.makeText(activity,"Couldn't get your profile\n${it.message}",Toast.LENGTH_LONG).show()
            }
    }


    override fun onDestroy() {
        super.onDestroy()
        if(auth.currentUser!=null) signOutAccount()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_activity_menu, menu);
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.signOut -> signOutAccount()
            R.id.editProfile -> updateProfile()
        }
        return true
    }

    private fun signOutAccount(){
        auth.signOut()
        (activity as MainActivity).loadFragment(LoginActivity())
    }

    private fun updateProfile() {
        (activity as MainActivity).loadFragment(ProfileEditActivity())
    }

    private fun dialogNewGroup()
    {
        val dialog = activity?.let { Dialog(it) }
        dialog!!.setContentView(R.layout.dialog_join_or_create)
        dialog.setTitle("Change avatar")
        dialog.findViewById<Button>(R.id.create_Bt).setOnClickListener {
            //loggedInAccount.group = Group()
            //loggedInAccount.group!!.addToGroup(auth.currentUser!!.uid)
            dialog.dismiss()
        }
        dialog.findViewById<Button>(R.id.join_Bt).setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
}