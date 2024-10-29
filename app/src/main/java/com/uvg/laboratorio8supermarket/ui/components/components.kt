package com.uvg.laboratorio8supermarket.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.uvg.laboratorio8supermarket.navigation.AppScreens

@Composable
fun Texto(texto: String){

    Text(
        text = texto,
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        fontSize = 25.sp,
        color = Color(0xFF00796B),
        fontWeight= FontWeight.Bold,
        fontStyle = FontStyle.Italic,
        lineHeight = 17.sp,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun TextoDescripcion(texto: String){

    Text(
        text = texto,
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        fontSize = 15.sp,
        color = Color(0xFF00796B),
        fontStyle = FontStyle.Normal,
        lineHeight = 17.sp,
        overflow = TextOverflow.Ellipsis
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavController, texto: String){
    TopAppBar(
        colors= TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
            ),
        navigationIcon = {
            IconButton(onClick = { navController.navigate(route= AppScreens.CategoriesScreen.route) })
            {
                Icon(
                    imageVector = Icons.Filled.Home, contentDescription = "home"
                )
            }
        },
        title={ Text(text = texto)}
    )
}