package com.evaluacionandroid.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class NationalityViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(NationalityViewModel::class.java)) {
            return NationalityViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}