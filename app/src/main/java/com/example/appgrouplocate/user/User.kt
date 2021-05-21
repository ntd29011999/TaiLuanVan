package com.example.appgrouplocate.user

import com.example.appgrouplocate.R
import com.google.android.gms.maps.model.LatLng

class User {
    constructor(email : String, password : String)
    {
        loginEmail = email
        loginPassword = password
    }

    constructor(){} //default constructor

    var loginEmail : String = ""
    var loginPassword : String = ""
    var name : String = ""
    var avtUrl = "https://firebasestorage.googleapis.com" +
            "/v0/b/model-nexus-311609.appspot.com/o/user_avt%2Fdefault_avt.jpg?" +
            "alt=media&token=c8b06ea8-d76c-4537-86f6-ae26c6917374"

    var group: Group? = null
    var currentLocation = LatLng(1.5,1.5)
}