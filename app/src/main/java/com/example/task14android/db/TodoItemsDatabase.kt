package com.example.task14android.db

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TodoItemData::class], version = 1, exportSchema = false)
abstract class TodoItemsDatabase : RoomDatabase() {
    companion object {
        private const val DB_NAME = "todo_db"

        @Volatile
        private var INSTANCE: TodoItemsDatabase? = null

        fun getInstance(appContext: Application): TodoItemsDatabase {
//            val tempInstance = INSTANCE
//            if (tempInstance != null) {
//                return tempInstance
//            }
//            val instance = Room.databaseBuilder(
//                appContext,
//                TodoItemsDatabase::class.java,
//                DB_NAME
//            ).allowMainThreadQueries()//TODO("!Only for testing! i.e. --> Remove this call")
//                .build()
//
////            return INSTANCE as TodoItemsDatabase
//            INSTANCE = instance
//            return instance

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    appContext,
                    TodoItemsDatabase::class.java,
                    DB_NAME
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

    abstract fun todoDao(): TodoDao
}