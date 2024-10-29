package com.uvg.laboratorio8supermarket.ui.categories.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.uvg.laboratorio8supermarket.R
import com.uvg.laboratorio8supermarket.ui.categories.viewmodel.CategoryViewModel
import com.uvg.laboratorio8supermarket.ui.components.TopBar
import com.uvg.laboratorio8supermarket.navigation.AppScreens

@Composable
fun CategoriesScreen(navController: NavHostController) {

    val viewModel: CategoryViewModel = viewModel()
    val categories = viewModel.categoriesState.value

    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(navController, "Categories")

        LazyColumn(modifier = Modifier.weight(1f)) {
            categories?.let {
                items(categories) { category ->
                    val str: String = category.strCategory
                    val description: String = category.strCategoryDescription
                    val thumb: String = category.strCategoryThumb

                    Card(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Row {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(thumb)
                                    .transformations()
                                    .size(500, 500)
                                    .build(),
                                contentDescription = "Image"
                            )
                            Column(modifier = Modifier.padding(6.dp)) {
                                Text(
                                    text = str,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontStyle = FontStyle.Italic,
                                    color = Color(0xFF00796B),
                                    lineHeight = 20.sp,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = description,
                                    fontSize = 8.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontStyle = FontStyle.Normal,
                                    lineHeight = 13.sp,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }
            }
        }

        // Barra de navegación inferior usando NavigationBar
        NavigationBar {
            NavigationBarItem(
                icon = { Icon(Icons.Filled.Home, contentDescription = "Supermarket") },
                label = { Text("Supermarket") },
                selected = false,
                onClick = { navController.navigate(AppScreens.SupermarketScreen.route) }
            )
            NavigationBarItem(
                icon = { Icon(Icons.Filled.Add, contentDescription = "Add Item") },
                label = { Text("Add Item") },
                selected = false,
                onClick = { navController.navigate(AppScreens.AddSupermarketItemScreen.route) }
            )
            NavigationBarItem(
                icon = { Icon(Icons.Filled.List, contentDescription = "Item List") },
                label = { Text("Item List") },
                selected = false,
                onClick = { navController.navigate(AppScreens.SupermarketItemListScreen.route) }
            )
        }
    }
}