package com.gustavoiensue.listafacil.uii

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable

fun LoginScreen(aoLogar: () -> Unit, aoNavegarCadastro: () -> Unit) {

    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }

    val corVerdePrincipal = Color(0xFF4CAF50)
    val corFundo = Color(0xFFE8F5E9)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(corFundo)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Logo carrinho e titulo
        Icon(
            imageVector = Icons.Filled.ShoppingCart,
            contentDescription = "Logo Lista Fácil",
            tint = corVerdePrincipal,
            modifier = Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "LISTA FÁCIL",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = corVerdePrincipal
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Cartão branco central com os campos
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
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = corVerdePrincipal,
                        focusedLabelColor = corVerdePrincipal
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = senha,
                    onValueChange = { senha = it },
                    label = { Text("Senha") },
                    visualTransformation = PasswordVisualTransformation(), // Esconde a senha com "bolinhas"
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = corVerdePrincipal,
                        focusedLabelColor = corVerdePrincipal
                    )
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = { aoLogar() }, // botao para tela principal
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = corVerdePrincipal),
                    shape = RoundedCornerShape(25.dp)
                ) {
                    Text("ENTRAR", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(16.dp))

                TextButton(onClick = { /* Faremos a ação de esqueci a senha depois */ }) {
                    Text("Esqueci minha senha", color = Color.Gray)
                }

                // 2. AQUI: Colocamos a ação no botão de Cadastro
                TextButton(onClick = { aoNavegarCadastro() }) {
                    // Texto com duas cores diferentes na mesma frase
                    val textoCadastro = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.Gray)) {
                            append("Não tem conta? ")
                        }
                        withStyle(style = SpanStyle(color = corVerdePrincipal, fontWeight = FontWeight.Bold)) {
                            append("Cadastre-se")
                        }
                    }
                    Text(textoCadastro)
                }
            }
        }
    }
}