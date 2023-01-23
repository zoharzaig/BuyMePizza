package com.example.buymepizza

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val address =  MutableLiveData<String>()
    val out : LiveData<String> get() = address

    fun setAddress( newAddress : String ){
        address.value=newAddress
    }

}

