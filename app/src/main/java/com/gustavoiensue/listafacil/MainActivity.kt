package com.gustavoiensue.listafacil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.room.Room
import com.gustavoiensue.listafacil.data.ListaFacilDatabase
import com.gustavoiensue.listafacil.uii.MinhasListasScreen
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
                // Chama a tela chamando o banco de dados
                MinhasListasScreen(dao = dao)
            }
        }
    }
}