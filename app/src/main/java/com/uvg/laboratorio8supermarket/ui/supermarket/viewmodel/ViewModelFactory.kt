package com.uvg.laboratorio8supermarket.ui.supermarket.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uvg.laboratorio8supermarket.database.SupermarketItemDao

class SupermarketViewModelFactory(
    private val dao: SupermarketItemDao
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SupermarketViewModel::class.java)) {
            return SupermarketViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
