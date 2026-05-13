package com.gustavoiensue.listafacil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.gustavoiensue.listafacil.data.ListaFacilDatabase
import com.gustavoiensue.listafacil.ui.theme.ListaFácilTheme
import com.gustavoiensue.listafacil.uii.CadastroScreen
import com.gustavoiensue.listafacil.uii.LoginScreen
import com.gustavoiensue.listafacil.uii.MapasScreen
import com.gustavoiensue.listafacil.uii.MinhasListasScreen
import com.gustavoiensue.listafacil.uii.PerfilScreen
import com.gustavoiensue.listafacil.uii.PromocoesScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ListaFácilTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val context = LocalContext.current

                    // 1. INICIALIZA O BANCO DE DADOS LOCAL (ROOM)
                    val db = Room.databaseBuilder(
                        context,
                        ListaFacilDatabase::class.java, "listafacil-db"
                    ).build()
                    val dao = db.itemDao()

                    // 2. VERIFICA SE JÁ TEM ALGUÉM LOGADO NO FIREBASE
                    val auth = FirebaseAuth.getInstance()
                    val usuarioLogado = auth.currentUser
                    val rotaInicial = if (usuarioLogado != null) "listas" else "login"

                    NavHost(navController = navController, startDestination = rotaInicial) {

                        // ROTA DE LOGIN
                        composable(route = "login") {
                            LoginScreen(
                                aoLogar = {
                                    navController.navigate(route = "listas") {
                                        popUpTo(route = "login") { inclusive = true }
                                    }
                                },
                                aoIrParaCadastro = {
                                    navController.navigate(route = "cadastro")
                                }
                            )
                        }

                        // ROTA DE CADASTRO
                        composable(route = "cadastro") {
                            CadastroScreen(
                                aoCadastrar = {
                                    navController.navigate(route = "listas") {
                                        popUpTo(route = "login") { inclusive = true }
                                    }
                                },
                                aoVoltarLogin = {
                                    navController.popBackStack()
                                }
                            )
                        }

                        // ROTA PRINCIPAL (Minhas Listas)
                        composable(route = "listas") {
                            // Aqui passamos o "dao" para a tela poder salvar e ler os itens!
                            MinhasListasScreen(
                                dao = dao,
                                aoIrParaPerfil = { navController.navigate("perfil") },
                                aoIrParaMaps = { navController.navigate("maps") },
                                aoIrParaPromocoes = { navController.navigate("promocoes") }
                            )
                        }
                        //Rota de promoções
                        composable(route = "promocoes") {
                            PromocoesScreen(aoVoltar = { navController.popBackStack() })
                        }

                        // ROTA DE PERFIL
                        composable(route = "perfil") {
                            PerfilScreen(
                                aoSair = {
                                    navController.navigate(route = "login") {
                                        popUpTo(0) { inclusive = true }
                                    }
                                }
                            )
                        }
                        //Rota do mapa
                        composable(route = "maps") {
                            MapasScreen(
                                aoVoltar = { navController.popBackStack() } // Volta para as listas
                            )
                        }
                    }
                }
            }
        }
    }
}