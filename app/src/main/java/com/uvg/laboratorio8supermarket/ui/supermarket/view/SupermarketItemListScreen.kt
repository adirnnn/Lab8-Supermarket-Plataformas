package com.uvg.laboratorio8supermarket.ui.supermarket.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.uvg.laboratorio8supermarket.networking.datac.SupermarketItem
import androidx.navigation.NavHostController
import androidx.compose.ui.platform.LocalContext
import com.uvg.laboratorio8supermarket.R

@Composable
fun SupermarketItemListScreen(
    items: List<SupermarketItem>,
    navController: NavHostController
) {
    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        items(items) { item ->
            SupermarketItemCard(item = item)
        }
    }
}

@Composable
fun SupermarketItemCard(item: SupermarketItem) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)) {

        Column(modifier = Modifier.padding(16.dp)) {
            // Mostrar nombre del artículo
            Text(text = "Artículo: ${item.itemName}")

            // Mostrar cantidad
            Text(text = "Cantidad: ${item.quantity}")

            // Mostrar imagen si existe
            item.imagePath?.let { imagePath ->
                val painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imagePath)
                        .crossfade(true)
                        .build()
                )

                Image(
                    painter = painter,
                    contentDescription = "Imagen del artículo",
                    modifier = Modifier
                        .size(150.dp)
                        .padding(8.dp),
                    contentScale = ContentScale.Crop
                )
            } ?: Text(text = "No hay imagen disponible")
        }
    }
}
