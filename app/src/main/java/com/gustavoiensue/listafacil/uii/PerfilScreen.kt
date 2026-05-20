package com.gustavoiensue.listafacil.uii

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth

@Composable
fun PerfilScreen(aoSair: () -> Unit, aoVoltar: () -> Unit) {
    val auth = FirebaseAuth.getInstance()
    val usuarioAtual = auth.currentUser

    // 2. Puxamos o e-mail
    val emailUsuario = usuarioAtual?.email ?: "Email não encontrado"

    val corVerdePrincipal = Color(color = 0xFF4CAF50)
    val corFundo = Color(color = 0xFFE8F5E9)


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = corFundo)
    ) {

        IconButton(
            onClick = { aoVoltar() },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.TopStart)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Voltar para o ecrã inicial",
                tint = corVerdePrincipal
            )
        }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // Foto de Perfil
            Icon(
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = "Foto de Perfil",
                tint = corVerdePrincipal,
                modifier = Modifier
                    .size(120.dp)
                    .background(Color.White, shape = CircleShape)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Cartão com as Informações
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Meus Dados",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = corVerdePrincipal
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Mostra o E-mail do usuário travado
                    OutlinedTextField(
                        value = emailUsuario,
                        onValueChange = {},
                        label = { Text("Email Cadastrado") },
                        readOnly = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = corVerdePrincipal,
                            unfocusedBorderColor = corVerdePrincipal
                        )
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Botão de Sair da Conta
                    Button(
                        onClick = {
                            auth.signOut() // Desloga do servidor do Google
                            aoSair()       // Aperta o gatilho para o Maestro mudar de tela
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53935)), // Vermelho para perigo
                        shape = RoundedCornerShape(25.dp)
                    ) {
                        Icon(
                            Icons.Filled.ExitToApp,
                            contentDescription = "Sair",
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("SAIR DA CONTA", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }

}