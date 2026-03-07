package com.gustavoiensue.listafacil.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    // Insere um novo item
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserirItem(item: ItemLista)

    // Deleta um item
    @Delete
    suspend fun deletarItem(item: ItemLista)

    // Busca todos os itens salvos para mostrar na sua tela "Minhas Listas"
    // O "Flow" faz com que a tela atualize em tempo real se algo mudar no banco!
    @Query("SELECT * FROM tabela_itens ORDER BY id DESC")
    fun buscarTodosItens(): Flow<List<ItemLista>>
}