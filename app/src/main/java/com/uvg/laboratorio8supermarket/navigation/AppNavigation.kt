package com.uvg.laboratorio8supermarket.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.uvg.laboratorio8supermarket.ui.categories.view.CategoriesScreen
import com.uvg.laboratorio8supermarket.ui.supermarket.view.AddSupermarketItemScreen
import com.uvg.laboratorio8supermarket.ui.supermarket.view.SupermarketItemListScreen
import com.uvg.laboratorio8supermarket.ui.supermarket.view.SupermarketScreen
import com.uvg.laboratorio8supermarket.ui.supermarket.viewmodel.SupermarketViewModel

@Composable
fun AppNavigation(viewModel: SupermarketViewModel) {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = AppScreens.CategoriesScreen.route) {
        composable(route = AppScreens.CategoriesScreen.route) {
            CategoriesScreen(navController = navController)
        }

        // Pantalla principal del supermercado
        composable(route = AppScreens.SupermarketScreen.route) {
            SupermarketScreen(viewModel = viewModel, context = context, navController = navController)
        }

        // Pantalla para agregar artículos
        composable(route = AppScreens.AddSupermarketItemScreen.route) {
            AddSupermarketItemScreen(
                context = context,
                viewModel = viewModel,
                navController = navController,
                onItemAdded = {
                    navController.navigate(AppScreens.SupermarketItemListScreen.route) // Navega a la lista después de agregar un artículo
                }
            )
        }

        // Pantalla para mostrar la lista de artículos
        composable(route = AppScreens.SupermarketItemListScreen.route) {
            SupermarketItemListScreen(items = viewModel.supermarketItems.value, navController = navController)
        }
    }
}
