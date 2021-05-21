package com.example.appgrouplocate.map

import android.location.Address
import com.google.android.gms.maps.model.LatLng

data class Place(
    val name : String,
    val latLng : LatLng,
    val address: LatLng,
    val rating : Float
)
