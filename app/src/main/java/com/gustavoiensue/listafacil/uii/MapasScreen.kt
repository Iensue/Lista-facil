package com.gustavoiensue.listafacil.uii

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapasScreen(aoVoltar: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mercados Próximos") },
                navigationIcon = {
                    IconButton(onClick = { aoVoltar() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { paddingValues ->
        // Coordenadas iniciais
        val localizacaoInicial = LatLng(-23.2754, -51.2778)

        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(localizacaoInicial, 15f)
        }

        GoogleMap(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            cameraPositionState = cameraPositionState
        ) {
            // Um marcador de exemplo
            Marker(
                state = MarkerState(position = LatLng(-23.2760, -51.2780)),
                title = "Supermercado Local",
                snippet = "Ideal para as compras do Lista Fácil!"
            )
        }
    }
}