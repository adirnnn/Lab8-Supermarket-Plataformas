package com.uvg.laboratorio8supermarket.ui.supermarket.view

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.uvg.laboratorio8supermarket.navigation.AppScreens
import com.uvg.laboratorio8supermarket.ui.supermarket.viewmodel.SupermarketViewModel

@Composable
fun SupermarketScreen(
    viewModel: SupermarketViewModel,
    context: Context,
    navController: NavHostController
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        // Botón para ir a la pantalla de agregar artículo
        Button(onClick = {
            navController.navigate(AppScreens.AddSupermarketItemScreen.route)
        }) {
            Text(text = "Agregar Artículo")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar la lista de artículos del supermercado
        SupermarketItemListScreen(
            items = viewModel.supermarketItems.value,
            navController = navController
        )
    }
}
