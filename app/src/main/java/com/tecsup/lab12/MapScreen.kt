package com.tecsup.lab12

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

@Composable
fun MapScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val arequipaLocation = LatLng(-16.4040102, -71.559611)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(arequipaLocation, 12f)
    }
    LaunchedEffect(Unit) {
        cameraPositionState.animate(
            update = CameraUpdateFactory.newLatLngZoom(LatLng(-16.2520984,-71.6836503), 12f), // Mover a Yura
            durationMs = 3000
        )
    }


    val locations = listOf(
        LatLng(-16.433415, -71.5442652), // JLByR
        LatLng(-16.4205151, -71.4945209), // Paucarpata
        LatLng(-16.3524187, -71.5675994)  // Zamacola
    )

    Box(modifier = modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            // Marcador personalizado principal (Arequipa)
            Marker(
                state = rememberMarkerState(position = arequipaLocation),
                icon = bitmapDescriptorFromVector(context, R.drawable.montana),
                title = "Arequipa, Perú"
            )

            // Marcadores secundarios
            locations.forEach { location ->
                Marker(
                    state = rememberMarkerState(position = location),
                    title = "Ubicación",
                    snippet = "Punto de interés"
                )
            }
        }
    }
}

fun bitmapDescriptorFromVector(context: Context, resId: Int): BitmapDescriptor {
    val original = BitmapFactory.decodeResource(context.resources, resId)
    val scaled = Bitmap.createScaledBitmap(original, 96, 96, false)
    return BitmapDescriptorFactory.fromBitmap(scaled)
}
