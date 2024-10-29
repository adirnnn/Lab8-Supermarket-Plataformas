package com.uvg.laboratorio8supermarket.ui.supermarket.view

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.uvg.laboratorio8supermarket.navigation.AppScreens
import com.uvg.laboratorio8supermarket.ui.supermarket.viewmodel.SupermarketViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AddSupermarketItemScreen(
    context: Context,
    viewModel: SupermarketViewModel,
    navController: NavHostController,
    onItemAdded: () -> Unit
) {
    var itemName by remember { mutableStateOf(TextFieldValue("")) }
    var quantity by remember { mutableStateOf(TextFieldValue("")) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        )
    }

    // Verificación de permisos de medios en Android 13 (API 33) o superior
    val hasMediaPermission by remember {
        mutableStateOf(
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.READ_MEDIA_IMAGES
                    ) == PackageManager.PERMISSION_GRANTED
        )
    }

    // Solicitar permisos para la cámara y acceso a medios
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val cameraGranted = permissions[Manifest.permission.CAMERA] ?: false
        val mediaGranted = permissions[Manifest.permission.READ_MEDIA_IMAGES] ?: false

        hasCameraPermission = cameraGranted

        if (!mediaGranted) {
            Toast.makeText(context, "Permiso de acceso a medios denegado", Toast.LENGTH_SHORT).show()
        }

        if (!cameraGranted) {
            Toast.makeText(context, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show()
        }
    }

    // Lanzador para capturar la imagen
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success) {
                imageUri?.let { uri ->
                    imageUri = uri
                    updateGallery(context, uri) // Actualiza la galería
                    Toast.makeText(context, "Imagen capturada con éxito", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Error al capturar la imagen", Toast.LENGTH_SHORT).show()
            }
        }
    )

    // Función para guardar la imagen en la carpeta pública "Pictures"
    val createImageFile = {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val storageDir: File = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        if (!storageDir.exists()) {
            storageDir.mkdirs() // Crear la carpeta si no existe
        }
        val imageFile = File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
        imageUri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", imageFile)

        // Retornar la ruta absoluta para guardarla en la base de datos
        imageFile.absolutePath
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Campo para itemName
        BasicTextField(
            value = itemName,
            onValueChange = { itemName = it },
            modifier = Modifier.fillMaxWidth(),
            decorationBox = { innerTextField ->
                if (itemName.text.isEmpty()) {
                    Text(text = "Nombre del artículo")
                }
                innerTextField()
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo para quantity
        BasicTextField(
            value = quantity,
            onValueChange = { quantity = it },
            modifier = Modifier.fillMaxWidth(),
            decorationBox = { innerTextField ->
                if (quantity.text.isEmpty()) {
                    Text(text = "Cantidad")
                }
                innerTextField()
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para capturar la imagen del artículo
        if (hasCameraPermission && hasMediaPermission) {
            Button(onClick = {
                val imagePath = createImageFile() // Crea el archivo de imagen
                imageUri?.let { cameraLauncher.launch(it) } // Lanza la cámara
            }) {
                Text(text = "Capturar Imagen")
            }
        } else {
            Button(onClick = {
                permissionLauncher.launch(
                    arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_MEDIA_IMAGES)
                ) // Solicitar permisos de cámara y medios
            }) {
                Text(text = "Solicitar Permisos")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para agregar el artículo a la lista
        Button(onClick = {
            val itemQuantity = quantity.text.toIntOrNull() ?: 0
            val imagePath = imageUri?.path

            // Verificar si los campos son válidos
            if (itemName.text.isNotEmpty() && itemQuantity > 0) {
                viewModel.addItem(itemName.text, itemQuantity, imagePath)

                // Notificar que el artículo ha sido agregado
                onItemAdded()

                // Navegar de vuelta a la pantalla del supermercado o lista
                navController.navigate(AppScreens.SupermarketScreen.route)
            }
        }) {
            Text(text = "Agregar Artículo")
        }
    }
}

// Función para actualizar la galería del sistema
fun updateGallery(context: Context, imageUri: Uri) {
    val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
    mediaScanIntent.data = imageUri
    context.sendBroadcast(mediaScanIntent)
}
