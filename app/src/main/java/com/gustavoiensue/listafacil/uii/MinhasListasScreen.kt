package com.gustavoiensue.listafacil.uii

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

    // rodar a função de deletar no banco de dados
    val coroutineScope = rememberCoroutineScope()

    val corVerdePrincipal = Color(0xFF4CAF50)
    val corFundo = Color(0xFFE8F5E9)

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
                onClick = { mostrarDialog = true },
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
                                .padding(horizontal = 16.dp, vertical = 8.dp), // Ajuste de padding
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically // Alinha tudo no meio
                        ) {
                            Text(text = item.nome, fontWeight = FontWeight.Bold, fontSize = 18.sp)

                            // Agrupa a Quantidade e o Botão de Lixeira
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "Qtd: ${item.quantidade}", color = Color.Gray)

                                IconButton(
                                    onClick = {
                                        // Deleta o item do banco de dados ao clicar
                                        coroutineScope.launch {
                                            dao.deletarItem(item)
                                        }
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Delete,
                                        contentDescription = "Deletar",
                                        tint = Color.Red // Deixa a lixeira vermelha
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        if (mostrarDialog) {
            DialogAdicionarItem(
                aoCancelar = { mostrarDialog = false },
                aoConfirmar = { _, _ -> mostrarDialog = false },
                dao = dao
            )
        }
    }
}

@Composable
fun DialogAdicionarItem(aoCancelar: () -> Unit, aoConfirmar: (String, String) -> Unit, dao: ItemDao) {
    var nome by remember { mutableStateOf("") }
    var quantidade by remember { mutableStateOf("") }
    val corVerdePrincipal = Color(0xFF4CAF50)
    val coroutineScope = rememberCoroutineScope()

    AlertDialog(
        onDismissRequest = aoCancelar,
        title = { Text("Adicionar Novo Item", fontWeight = FontWeight.Bold, color = corVerdePrincipal) },
        text = {
            Column {
                OutlinedTextField(
                    value = nome,
                    onValueChange = { nome = it },
                    label = { Text("Nome do Item") },
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = quantidade,
                    onValueChange = { quantidade = it },
                    label = { Text("Quantidade") },
                    singleLine = true
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (nome.isNotBlank()) {
                        coroutineScope.launch {
                            dao.inserirItem(ItemLista(nome = nome, quantidade = quantidade))
                            aoConfirmar(nome, quantidade)
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = corVerdePrincipal)
            ) {
                Text("Adicionar")
            }
        },
        dismissButton = {
            TextButton(onClick = aoCancelar) { Text("Cancelar", color = Color.Gray) }
        }
    )
}