package com.gustavoiensue.listafacil.uii

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gustavoiensue.listafacil.data.ItemDao
import com.gustavoiensue.listafacil.data.ItemLista
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MinhasListasScreen(dao: ItemDao) {
    val itensDaLista by dao.buscarTodosItens().collectAsState(initial = emptyList())
    var mostrarDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val corVerdePrincipal = Color(0xFF4CAF50)
    val corFundo = Color(0xFFE8F5E9)

    // 1. Envolvemos TODA a tela numa Box principal.
    // Isso nos dá o poder de colocar coisas literalmente "por cima" de tudo.
    Box(modifier = Modifier.fillMaxSize()) {

        // 2. A sua tela normal fica aqui na camada do fundo
        Scaffold(
            containerColor = corFundo,
            topBar = {
                TopAppBar(
                    title = { Text("Minhas Listas", fontWeight = FontWeight.Bold, color = corVerdePrincipal) },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = corFundo)
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { mostrarDialog = true }, // Ativa o nosso Pop-up customizado
                    containerColor = corVerdePrincipal,
                    contentColor = Color.White,
                    shape = RoundedCornerShape(50)
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Adicionar")
                }
            }
        ) { paddingValues ->
            if (itensDaLista.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Você ainda não tem nenhuma lista", fontWeight = FontWeight.Bold, color = corVerdePrincipal)
                    Text("Clique no + para começar", fontSize = 14.sp, color = corVerdePrincipal)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(itensDaLista) { item ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = item.nome, fontWeight = FontWeight.Bold, fontSize = 18.sp)

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(text = "Qtd: ${item.quantidade}", color = Color.Gray)

                                    IconButton(
                                        onClick = {
                                            coroutineScope.launch { dao.deletarItem(item) }
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.Delete,
                                            contentDescription = "Deletar",
                                            tint = Color.Red
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // 3. A MÁGICA: O nosso Pop-up blindado (Overlay)
        // Se mostrarDialog for true, ele desenha isso por cima da tela
        if (mostrarDialog) {

            // Fundo da tela escurecido
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.6f)) // Fundo preto transparente
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        // Se o usuário clicar no fundo escuro, o Pop-up fecha
                        mostrarDialog = false
                    },
                contentAlignment = Alignment.Center // Centraliza o cartão branco
            ) {

                // Cartão branco do formulário
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp)
                        .clickable( // Impede que clicar dentro do cartão feche a tela
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {},
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    // Estado dos textos do formulário
                    var nome by remember { mutableStateOf("") }
                    var quantidade by remember { mutableStateOf("") }

                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Adicionar Novo Item",
                            fontWeight = FontWeight.Bold,
                            color = corVerdePrincipal,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        OutlinedTextField(
                            value = nome,
                            onValueChange = { nome = it },
                            label = { Text("Nome do Item") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = quantidade,
                            onValueChange = { quantidade = it },
                            label = { Text("Quantidade") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            TextButton(onClick = { mostrarDialog = false }) {
                                Text("Cancelar", color = Color.Gray)
                            }
                            Button(
                                onClick = {
                                    if (nome.isNotBlank()) {
                                        coroutineScope.launch {
                                            dao.inserirItem(ItemLista(nome = nome, quantidade = quantidade))
                                            mostrarDialog = false // Fecha ao salvar
                                        }
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = corVerdePrincipal)
                            ) {
                                Text("Adicionar")
                            }
                        }
                    }
                }
            }
        }
    }
}