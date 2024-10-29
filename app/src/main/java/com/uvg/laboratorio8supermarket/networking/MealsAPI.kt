package com.uvg.laboratorio8supermarket.networking

import com.uvg.laboratorio8supermarket.networking.datac.CategoriesDC
import retrofit2.http.GET

interface MealsApi {

    @GET("categories.php")
    suspend fun getCategories(): CategoriesDC

}


