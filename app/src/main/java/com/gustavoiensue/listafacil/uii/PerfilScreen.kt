package com.gustavoiensue.listafacil.uii

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PerfilScreen(aoSair: () -> Unit) {
    val corVerdePrincipal = Color(0xFF4CAF50)
    val corVerdeEscuro = Color(0xFF006400) // Verde mais escuro para o botão de sair
    val corFundo = Color(0xFFE8F5E9)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(corFundo)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título no topo
        Text(
            text = "Perfil",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = corVerdePrincipal,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Cartão 1: Informações do Usuário
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // "Foto" de perfil (Círculo cinza)
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .background(Color.LightGray, shape = CircleShape)
                )

                Spacer(modifier = Modifier.width(16.dp))

                // Nome e Email
                Column {
                    Text(
                        text = "Gustavo Iensue",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = corVerdePrincipal
                    )
                    Text(
                        text = "g...e@email.com",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Cartão 2: Menu de Opções
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column {
                ItemMenuPerfil(icone = Icons.Filled.Lock, texto = "Alterar Senha")
                Divider(color = Color(0xFFEEEEEE), thickness = 1.dp)

                ItemMenuPerfil(icone = Icons.Filled.Notifications, texto = "Notificações")
                Divider(color = Color(0xFFEEEEEE), thickness = 1.dp)

                ItemMenuPerfil(icone = Icons.Filled.Info, texto = "Informações")
                Divider(color = Color(0xFFEEEEEE), thickness = 1.dp)

                ItemMenuPerfil(icone = Icons.Filled.List, texto = "Listas Salvas")
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Botão de Sair
        Button(
            onClick = { aoSair() },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = corVerdeEscuro),
            shape = RoundedCornerShape(25.dp)
        ) {
            Text("Sair", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}

// Componente reutilizável para cada linha do menu
@Composable
fun ItemMenuPerfil(icone: ImageVector, texto: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icone,
                contentDescription = texto,
                tint = Color.Gray,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = texto, fontSize = 16.sp, color = Color.DarkGray)
        }
        Icon(
            imageVector = Icons.Filled.KeyboardArrowRight,
            contentDescription = "Ir",
            tint = Color.LightGray
        )
    }
}