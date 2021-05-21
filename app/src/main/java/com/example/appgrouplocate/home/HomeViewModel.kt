package com.example.appgrouplocate.home

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appgrouplocate.user.User
import com.google.firebase.database.MutableData

class HomeViewModel : ViewModel() {
    var dataShow = MutableLiveData<User>()
}