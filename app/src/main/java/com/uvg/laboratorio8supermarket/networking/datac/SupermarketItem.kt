package com.uvg.laboratorio8supermarket.networking.datac

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "supermarket_items")
data class  SupermarketItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val itemName: String,
    val quantity: Int,
    val imagePath: String? = null // Este campo es opcional
)