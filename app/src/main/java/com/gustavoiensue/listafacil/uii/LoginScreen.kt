package com.gustavoiensue.listafacil.uii

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginScreen(aoLogar: () -> Unit, aoIrParaCadastro: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }

    // Ferramentas necessárias para o Firebase e para mostrar avisos na tela
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()

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
        // Ícone do Aplicativo
        Icon(
            imageVector = Icons.Filled.ShoppingCart,
            contentDescription = "Ícone do Lista Fácil",
            tint = corVerdePrincipal,
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "LISTA FÁCIL",
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold,
            color = corVerdePrincipal
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Cartão branco de Login
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
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = corVerdePrincipal, focusedLabelColor = corVerdePrincipal)
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = senha,
                    onValueChange = { senha = it },
                    label = { Text("Senha") },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = corVerdePrincipal, focusedLabelColor = corVerdePrincipal)
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        // Lógica de Login com Firebase
                        if (email.isNotEmpty() && senha.isNotEmpty()) {
                            auth.signInWithEmailAndPassword(email, senha)
                                .addOnCompleteListener { tarefa ->
                                    if (tarefa.isSuccessful) {
                                        Toast.makeText(context, "Bem-vindo de volta!", Toast.LENGTH_SHORT).show()
                                        aoLogar() // Vai para a tela principal
                                    } else {
                                        Toast.makeText(context, "Erro: Email ou senha incorretos", Toast.LENGTH_LONG).show()
                                    }
                                }
                        } else {
                            Toast.makeText(context, "Preencha email e senha", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = corVerdePrincipal),
                    shape = RoundedCornerShape(25.dp)
                ) {
                    Text("ENTRAR", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(16.dp))

                TextButton(onClick = { aoIrParaCadastro() }) {
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