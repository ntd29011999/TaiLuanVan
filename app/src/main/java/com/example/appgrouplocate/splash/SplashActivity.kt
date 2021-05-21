package com.example.appgrouplocate.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.appgrouplocate.main.MainActivity
import com.example.appgrouplocate.R
import com.example.appgrouplocate.database.auth

class SplashActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if(auth.currentUser!=null) auth.signOut()

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}