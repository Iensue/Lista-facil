package com.gustavoiensue.listafacil.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ItemLista::class], version = 1, exportSchema = false)
abstract class ListaFacilDatabase : RoomDatabase() {

    abstract fun itemDao(): ItemDao

}