package com.uvg.laboratorio8supermarket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.uvg.laboratorio8supermarket.database.AppDatabase
import com.uvg.laboratorio8supermarket.navigation.AppNavigation
import com.uvg.laboratorio8supermarket.ui.supermarket.viewmodel.SupermarketViewModel
import com.uvg.laboratorio8supermarket.ui.supermarket.viewmodel.SupermarketViewModelFactory
import com.uvg.laboratorio8supermarket.ui.theme.Laboratorio8SupermarketTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Obtener la instancia de la base de datos
        val database = AppDatabase.getDatabase(this)
        val supermarketDao = database.supermarketItemDao()

        // Crear la fábrica de ViewModel
        val viewModelFactory = SupermarketViewModelFactory(supermarketDao)

        // Crear la instancia del ViewModel
        val viewModel = ViewModelProvider(this, viewModelFactory).get(SupermarketViewModel::class.java)

        setContent {
            Laboratorio8SupermarketTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(viewModel = viewModel)
                }
            }
        }
    }
}
