package com.uvg.laboratorio8supermarket.navigation

sealed class AppScreens(val route: String){
    object CategoriesScreen: AppScreens("primera")
    object AddSupermarketItemScreen : AppScreens("segundaA")
    object SupermarketItemListScreen : AppScreens("segundaB")
    object SupermarketScreen : AppScreens("segunda")
}