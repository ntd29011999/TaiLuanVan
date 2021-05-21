package com.example.appgrouplocate.database

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

val database : DatabaseReference = Firebase.database.reference
val auth : FirebaseAuth = Firebase.auth
val storage = Firebase.storage.reference