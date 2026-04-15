package com.gustavoiensue.listafacil.uii

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gustavoiensue.listafacil.data.ItemDao
import com.gustavoiensue.listafacil.data.ItemLista
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MinhasListasScreen(dao: ItemDao, aoIrParaPerfil: () -> Unit, aoIrParaMaps: () -> Unit) {
    val itensDaLista by dao.buscarTodosItens().collectAsState(initial = emptyList())
    var mostrarDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val corVerdePrincipal = Color(0xFF4CAF50)
    val corFundo = Color(0xFFE8F5E9)

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            containerColor = corFundo,
            topBar = {
                TopAppBar(
                    title = {
                        Text("Minhas Listas", fontWeight = FontWeight.ExtraBold, color = corVerdePrincipal, fontSize = 28.sp)
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
                )
            },
            bottomBar = {
                NavigationBar(containerColor = Color.White) {
                    NavigationBarItem(
                        icon = { Icon(Icons.Filled.List, "Listas") },
                        label = { Text("Listas") },
                        selected = true,
                        onClick = { },
                        colors = NavigationBarItemDefaults.colors(selectedIconColor = corVerdePrincipal, selectedTextColor = corVerdePrincipal, indicatorColor = corFundo)
                    )
                    NavigationBarItem(icon = { Icon(Icons.Filled.ShoppingCart, "Promo") }, label = { Text("Promo") }, selected = false, onClick = { })
                    NavigationBarItem(icon = { Icon(Icons.Filled.LocationOn, "Maps") }, label = { Text("Maps") }, selected = false,onClick = { aoIrParaMaps() })
                    NavigationBarItem(icon = { Icon(Icons.Filled.Person, "Perfil") }, label = { Text("Perfil") }, selected = false, onClick = { aoIrParaPerfil() })
                }
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
                    Text("Sua lista está vazia", fontWeight = FontWeight.Bold, color = corVerdePrincipal)
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
                                modifier = Modifier.fillMaxWidth().padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                                    // CHECKBOX PARA MARCAR COMO COMPRADO
                                    Checkbox(
                                        checked = item.isComprado,
                                        onCheckedChange = { isChecked ->
                                            coroutineScope.launch {
                                                // Atualiza o item no banco (REPLACE)
                                                dao.inserirItem(item.copy(isComprado = isChecked))
                                            }
                                        },
                                        colors = CheckboxDefaults.colors(checkedColor = corVerdePrincipal)
                                    )

                                    Spacer(modifier = Modifier.width(8.dp))

                                    Column {
                                        Text(
                                            text = item.nome,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 18.sp,
                                            // EFEITO VISUAL: Se comprado, risca o texto
                                            textDecoration = if (item.isComprado) TextDecoration.LineThrough else TextDecoration.None,
                                            color = if (item.isComprado) Color.Gray else Color.Black
                                        )
                                        Text(text = "Qtd: ${item.quantidade}", fontSize = 14.sp, color = Color.Gray)
                                    }
                                }

                                IconButton(onClick = { coroutineScope.launch { dao.deletarItem(item) } }) {
                                    Icon(Icons.Filled.Delete, "Deletar", tint = Color.Red)
                                }
                            }
                        }
                    }
                }
            }
        }

        // POP-UP
        if (mostrarDialog) {
            Box(
                modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.6f))
                    .clickable(interactionSource = remember { MutableInteractionSource() }, indication = null) { mostrarDialog = false },
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth().padding(32.dp).clickable(interactionSource = remember { MutableInteractionSource() }, indication = null) {},
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    var nome by remember { mutableStateOf("") }
                    var quantidade by remember { mutableStateOf("") }
                    Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Novo Item", fontWeight = FontWeight.Bold, color = corVerdePrincipal, fontSize = 20.sp)
                        OutlinedTextField(value = nome, onValueChange = { nome = it }, label = { Text("Nome") }, modifier = Modifier.fillMaxWidth())
                        OutlinedTextField(value = quantidade, onValueChange = { quantidade = it }, label = { Text("Qtd") }, modifier = Modifier.fillMaxWidth())
                        Spacer(modifier = Modifier.height(24.dp))
                        Button(
                            onClick = {
                                if (nome.isNotBlank()) {
                                    coroutineScope.launch {
                                        dao.inserirItem(ItemLista(nome = nome, quantidade = quantidade))
                                        mostrarDialog = false
                                    }
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = corVerdePrincipal)
                        ) { Text("Adicionar") }
                    }
                }
            }
        }
    }
}