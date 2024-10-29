package com.uvg.laboratorio8supermarket.ui.categories.repository

import com.uvg.laboratorio8supermarket.database.CategoryDao
import com.uvg.laboratorio8supermarket.networking.MealWebService
import com.uvg.laboratorio8supermarket.networking.datac.CategoriesDC
import com.uvg.laboratorio8supermarket.networking.datac.Category

class CategoryRepository(
    private val webService: MealWebService = MealWebService(),
    private val categoryDao: CategoryDao
) {

    // Cargar las categorías desde la API y almacenarlas en Room
    suspend fun refreshCategories(): CategoriesDC {
        val categoriesFromApi = webService.getMealsCategories()
        categoryDao.insertAll(categoriesFromApi.categories) // Guardar en Room
        return categoriesFromApi
    }

    // Obtener las categorías almacenadas en Room
    suspend fun getCategoriesFromCache(): List<Category> {
        return categoryDao.getAllCategories()
    }

    // Obtener las categorías ya sea desde la API o Room
    suspend fun getCategories(): List<Category> {
        val cachedCategories = getCategoriesFromCache()
        return if (cachedCategories.isEmpty()) {
            // Si la caché está vacía, actualizar con los datos de la API
            refreshCategories().categories
        } else {
            cachedCategories
        }
    }
}
