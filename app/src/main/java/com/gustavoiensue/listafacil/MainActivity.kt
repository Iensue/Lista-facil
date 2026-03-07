package com.gustavoiensue.listafacil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.gustavoiensue.listafacil.data.ListaFacilDatabase
import com.gustavoiensue.listafacil.uii.CadastroScreen
import com.gustavoiensue.listafacil.uii.LoginScreen
import com.gustavoiensue.listafacil.uii.MinhasListasScreen
import com.gustavoiensue.listafacil.uii.PerfilScreen
import com.gustavoiensue.listafacil.ui.theme.ListaFácilTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa o banco de dados Room
        val db = Room.databaseBuilder(
            applicationContext,
            ListaFacilDatabase::class.java, "banco-lista-facil"
        ).build()

        val dao = db.itemDao()

        enableEdgeToEdge()
        setContent {
            ListaFácilTheme {
                val navController = rememberNavController()

                // Variável que "escuta" em qual ecrã (rota) o utilizador está no momento
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val rotaAtual = navBackStackEntry?.destination?.route

                val corVerdePrincipal = Color(0xFF4CAF50)

                // O Scaffold global vai segurar a nossa barra inferior
                Scaffold(
                    bottomBar = {
                        // Só mostra a barra inferior se NÃO estiver no login nem no cadastro
                        if (rotaAtual != "login" && rotaAtual != "cadastro") {
                            NavigationBar(
                                containerColor = Color.White
                            ) {
                                // 1. Botão Listas
                                NavigationBarItem(
                                    icon = { Icon(Icons.Filled.ShoppingCart, contentDescription = "Listas") },
                                    label = { Text("Listas") },
                                    selected = rotaAtual == "listas",
                                    onClick = {
                                        navController.navigate("listas") {
                                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    },
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = corVerdePrincipal,
                                        selectedTextColor = corVerdePrincipal,
                                        indicatorColor = Color(0xFFE8F5E9)
                                    )
                                )

                                // 2. Botão Promoções (Sem ação por enquanto)
                                NavigationBarItem(
                                    icon = { Icon(Icons.Filled.Star, contentDescription = "Promoções") },
                                    label = { Text("Promoções") },
                                    selected = rotaAtual == "promocoes",
                                    onClick = { /* Faremos o ecrã de promoções no futuro */ }
                                )

                                // 3. Botão Mapa (Sem ação por enquanto)
                                NavigationBarItem(
                                    icon = { Icon(Icons.Filled.Place, contentDescription = "Mapa") },
                                    label = { Text("Mapa") },
                                    selected = rotaAtual == "mapa",
                                    onClick = { /* Faremos o ecrã do mapa no futuro */ }
                                )

                                // 4. Botão Perfil
                                NavigationBarItem(
                                    icon = { Icon(Icons.Filled.Person, contentDescription = "Perfil") },
                                    label = { Text("Perfil") },
                                    selected = rotaAtual == "perfil",
                                    onClick = {
                                        navController.navigate("perfil") {
                                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    },
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = corVerdePrincipal,
                                        selectedTextColor = corVerdePrincipal,
                                        indicatorColor = Color(0xFFE8F5E9)
                                    )
                                )
                            }
                        }
                    }
                ) { paddingValues ->

                    // O NavHost é o "mapa" da aplicação. Volta a iniciar no "login".
                    // O modifier.padding garante que os ecrãs não fiquem escondidos atrás da barra
                    NavHost(
                        navController = navController,
                        startDestination = "login",
                        modifier = Modifier.padding(paddingValues)
                    ) {

                        composable("login") {
                            LoginScreen(
                                aoLogar = {
                                    navController.navigate("listas") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                },
                                aoNavegarCadastro = {
                                    navController.navigate("cadastro")
                                }
                            )
                        }

                        composable("cadastro") {
                            CadastroScreen(
                                aoCadastrar = {
                                    navController.navigate("listas") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                },
                                aoVoltarLogin = {
                                    navController.popBackStack()
                                }
                            )
                        }

                        composable("listas") {
                            MinhasListasScreen(dao = dao)
                        }

                        composable("perfil") {
                            PerfilScreen(
                                aoSair = {
                                    navController.navigate("login") {
                                        popUpTo("listas") { inclusive = true }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}