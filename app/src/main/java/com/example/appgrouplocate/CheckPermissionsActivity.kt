package com.example.appgrouplocate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.appgrouplocate.map.MapsActivity

class CheckPermissionsActivity : AppCompatActivity() {

    private var permission = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.ACCESS_COARSE_LOCATION)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_permissions)
        
        val intent = Intent(this@CheckPermissionsActivity, MapsActivity::class.java)

        startActivity(intent)
    }
}