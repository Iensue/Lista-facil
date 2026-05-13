package com.gustavoiensue.listafacil.uii

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PromocoesScreen(aoVoltar: () -> Unit) {
    val corVerdePrincipal = Color(0xFF4CAF50)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Promoções do Dia", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { aoVoltar() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Filled.ShoppingCart,
                contentDescription = null,
                modifier = Modifier.size(100.dp),
                tint = Color.LightGray
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Nenhuma promoção disponível hoje",
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                fontSize = 18.sp
            )
            Text(
                text = "Tente novamente mais tarde!",
                color = Color.LightGray,
                fontSize = 14.sp
            )
        }
    }
}