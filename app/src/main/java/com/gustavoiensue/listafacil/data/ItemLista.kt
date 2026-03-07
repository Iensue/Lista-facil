package com.gustavoiensue.listafacil.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tabela_itens")
data class ItemLista(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nome: String,
    val quantidade: String,
    val isComprado: Boolean = false
)