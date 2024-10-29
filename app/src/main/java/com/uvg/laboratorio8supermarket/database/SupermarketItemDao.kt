package com.uvg.laboratorio8supermarket.database

import androidx.room.*
import com.uvg.laboratorio8supermarket.networking.datac.SupermarketItem

@Dao
interface SupermarketItemDao {

    // Insertar un artículo nuevo
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: SupermarketItem)

    // Actualizar un artículo existente
    @Update
    suspend fun updateItem(item: SupermarketItem)

    @Query("UPDATE supermarket_items SET imagePath = :imagePath WHERE id = :itemId")
    suspend fun updateImagePath(itemId: Int, imagePath: String)

    // Eliminar un artículo específico
    @Delete
    suspend fun deleteItem(item: SupermarketItem)

    // Consultar todos los artículos en la base de datos
    @Query("SELECT * FROM supermarket_items")
    suspend fun getAllItems(): List<SupermarketItem>

    // Consultar un artículo por su ID
    @Query("SELECT * FROM supermarket_items WHERE id = :itemId")
    suspend fun getItemById(itemId: Int): SupermarketItem?
}
