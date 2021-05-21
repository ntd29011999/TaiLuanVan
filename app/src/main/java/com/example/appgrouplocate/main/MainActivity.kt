package com.example.appgrouplocate.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.appgrouplocate.R
import com.example.appgrouplocate.database.auth
import com.example.appgrouplocate.database.database
import com.example.appgrouplocate.home.HomeActivity
import com.example.appgrouplocate.login.LoginActivity
import com.example.appgrouplocate.user.Group
import com.example.appgrouplocate.user.User
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        loadFragment(LoginActivity())
    }

    fun loadFragment(fragment : Fragment)
    {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentsContainer_Fl,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
        title = fragment.toString()
    }

    var loggedInAccount = User()
    var joinedGroup = Group()

    //pull user's data to local account
    fun parseData()
    {
        database.child("users").child(auth.currentUser!!.uid).addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                loggedInAccount.apply {
                    name = snapshot.child("name").value.toString()
                    avtUrl = snapshot.child("avtUrl").value.toString()
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e("parse data","parse data failed: \n ${error.message}")
            }

        })
    }
}