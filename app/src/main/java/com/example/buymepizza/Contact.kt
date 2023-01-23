package com.example.buymepizza

import android.content.ClipData
import androidx.fragment.app.viewModels

data class Contact(val name:String, val number: String)

object ItemsManger {

    val contactsList : MutableList<Contact> = mutableListOf()

    fun add( contact : Contact ) {
        contactsList.add(contact)
    }

    fun remove(index:Int){
        contactsList.removeAt(index)
    }
}